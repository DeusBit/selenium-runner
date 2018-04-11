package org.deeprim.selenium.action

import org.deeprim.selenium.driver.RemoteWebDriverWrapper
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * @author Dmytro Primshyts
 */
interface ISeleniumActionContext {

  /**
   * Get [RemoteWebDriver].
   */
  val driver: RemoteWebDriverWrapper

}
