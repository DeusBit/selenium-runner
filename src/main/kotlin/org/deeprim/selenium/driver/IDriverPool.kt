package org.deeprim.selenium.driver

import org.openqa.selenium.remote.RemoteWebDriver

/**
 * @author Dmytro Primshyts
 */
interface IDriverPool {

  /**
   * Obtain [RemoteWebDriver].
   *
   * The invocation will block the
   * thread until free driver is available.
   */
  val driver: RemoteWebDriverWrapper

}
