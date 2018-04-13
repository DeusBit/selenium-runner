package org.deeprim.selenium

import kotlinx.coroutines.experimental.Deferred
import org.deeprim.selenium.action.ISeleniumActionContext

/**
 * @author Dmytro Primshyts
 */
interface ISeleniumRunner {

  /**
   * Execute an action within [ISeleniumActionContext].
   *
   * @param action action to execute
   */
  fun execute(action: ISeleniumActionContext.() -> Unit) : Deferred<Unit>

  /**
   * Destroy current [ISeleniumRunner].
   * All 'in progress' actions will be discarded.
   * Used resources freed.
   * The executor can not be reused after destroy.
   */
  fun destroy()

  companion object {

    /**
     * Create instance of selenium runner.
     *
     * @param poolSize max parallel
     * actions allowed within current runner (2 by `default`)
     *
     * @return instance of selenium runner
     */
    fun create(
        poolSize: Int = 2
    ): ISeleniumRunner {
      return SeleniumRunner(poolSize)
    }
  }

}
