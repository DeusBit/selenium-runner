package org.deeprim.selenium.driver

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.logging.Level

/**
 * @author Dmytro Primshyts
 */
class DriverPool(size: Int) : IDriverPoolInternal {

    private val freeDrivers: BlockingQueue<PooledDriver> = ArrayBlockingQueue(size)
    private val usedDrivers: MutableList<PooledDriver> = ArrayList()

    @Volatile
    private var closed = false

    init {
        WebDriverManager.chromedriver().setup()
        for (i in 1..size) {
            freeDrivers.offer(createPooledDriver())
        }
    }

    override fun close() {
        closed = true
        (usedDrivers + freeDrivers).forEach {
            it.chromeDriverService.stop()
        }

        freeDrivers.clear()
        usedDrivers.clear()
    }

    override fun release(driver: RemoteWebDriverWrapper) {
        require(!closed, { "The pool is closed!" })

        val pooledDriver = usedDrivers.find {
            it.remoteWebDriver == driver
        } ?: return

        freeDrivers.put(pooledDriver)
        usedDrivers.remove(pooledDriver)
    }

    override val driver: RemoteWebDriverWrapper
        get() {
            require(!closed, { "The pool is closed!" })

            val result = freeDrivers.take()
            usedDrivers.add(result)
            return result.remoteWebDriver
        }

    private fun createPooledDriver(): PooledDriver {
        val service = ChromeDriverService.Builder()
                .usingAnyFreePort()
                .build()

        service.start()

        val logPrefs = LoggingPreferences().apply {
            enable(LogType.BROWSER, Level.ALL)
        }

        val capabilities = DesiredCapabilities.chrome()
                .merge(ChromeOptions().setHeadless(true)).apply {
                    setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
                }

        val remoteWebDriver = RemoteWebDriver(
                service.url,
                capabilities)

        return PooledDriver(service,
                RemoteWebDriverWrapper(remoteWebDriver))
    }

    private inner class PooledDriver(
            val chromeDriverService: ChromeDriverService,
            val remoteWebDriver: RemoteWebDriverWrapper
    )

}
