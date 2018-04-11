package org.deeprim.selenium.driver

import org.openqa.selenium.HasCapabilities
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.HasInputDevices
import org.openqa.selenium.interactions.Interactive
import org.openqa.selenium.internal.*
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * [RemoteWebDriver] wrapper that intercepts
 * call to [RemoteWebDriver.close] method so container driver pool
 * could reuse the original web driver.
 *
 * @author Dmytro Primshyts
 */
class RemoteWebDriverWrapper internal constructor(
        private val driver: RemoteWebDriver
) : WebDriver by driver,
        JavascriptExecutor by driver,
        FindsById by driver,
        FindsByClassName by driver,
        FindsByLinkText by driver,
        FindsByName by driver,
        FindsByCssSelector by driver,
        FindsByTagName by driver,
        FindsByXPath by driver,
        HasInputDevices by driver,
        HasCapabilities by driver,
        Interactive by driver,
        TakesScreenshot by driver
