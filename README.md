# selenium-runner

[![](https://jitpack.io/v/deeprim/selenium-runner.svg)](https://jitpack.io/#deeprim/selenium-runner)

### Pooled headless chrome selenium runner

Usage:

```kotlin
repositories {
  maven { 
    setUrl("https://jitpack.io") 
  }
}

dependencies {
  compile("com.github.deeprim:selenium-runner:0.1.1")
}
```

Then you may use it as follows:

```kotlin
fun main(args: Array<String>) : Unit {
  val runner = ISeleniumRunner.create(10) 
  
  listOf("page1", "page2", "page3").forEach { page -> {
    runner.execute {
      driver.get(page)

      // do stuff with page
    }
  }
  
  runner.destroy()
}  
```


