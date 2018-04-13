package org.deeprim.selenium

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import org.deeprim.selenium.action.ISeleniumActionContext
import org.deeprim.selenium.action.SeleniumActionContext
import org.deeprim.selenium.driver.DriverPool
import org.deeprim.selenium.driver.IDriverPoolInternal

/**
 * @author Dmytro Primshyts
 */
class SeleniumRunner(
    poolSize: Int
) : ISeleniumRunner,
    IDriverPoolInternal by DriverPool(poolSize) {

  private val threadPool = newFixedThreadPoolContext(poolSize, "Selenium Runner Pool")

  override fun execute(action: ISeleniumActionContext.() -> Unit) = async(threadPool) {
    val myDriver = driver
    try {
      action.invoke(SeleniumActionContext(myDriver))
    } finally {
      release(myDriver)
    }
  }

  override fun destroy() {
    threadPool.close()
    close()
  }

}
