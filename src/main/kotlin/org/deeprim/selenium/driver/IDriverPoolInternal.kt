package org.deeprim.selenium.driver

/**
 * @author Dmytro Primshyts
 */
internal interface IDriverPoolInternal : IDriverPool {

  /**
   * Release given driver object.
   *
   * @param driver the driver to release
   */
  fun release(driver: RemoteWebDriverWrapper)

  /**
   * Close the pool.
   */
  fun close()

}
