package org.deeprim.selenium

import kotlinx.coroutines.experimental.Deferred
import org.deeprim.selenium.action.ISeleniumActionContext

/**
 * @author Dmytro Primshyts
 */
interface ISeleniumExecutor {

  /**
   * Execute an action within [ISeleniumActionContext].
   *
   * @param action action to execute
   */
  fun execute(action: ISeleniumActionContext.() -> Unit) : Deferred<Unit>

  /**
   * Destroy current [ISeleniumExecutor].
   * All 'in progress' actions will be discarded.
   * Used resources freed.
   * The executor can not be reused after destroy.
   */
  fun destroy()

  companion object {

    /**
     * Create instance of selenium executor.
     *
     * @param maxParallelActions max parallel
     * actions allowed within current executor (2 by `default`)
     *
     * @return instance of selenium executor
     */
    fun create(
        maxParallelActions: Int = 2
    ): ISeleniumExecutor {
      return SeleniumExecutor(maxParallelActions)
    }
  }

}
