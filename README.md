# old-slider
[![](https://jitpack.io/v/joelnah/old-slider.svg)](https://jitpack.io/#joelnah/old-slider)
[![](https://img.shields.io/badge/Android%20API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![](https://img.shields.io/badge/Kotlin-1.8.0-blue.svg)](http://kotlinlang.org)
[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)


## Overview
- I just didn't like the current Material Design slider, so I made it look like the old version.

## Setting
```kotlin
//settings.gradle
maven(url = "https://jitpack.io")
//build.gradle
implementation ("com.github.joelnah:old-slider:Tag")
```

## Usage

![screenshot1][1]
```kotlin
OldSlider(
    value = value1,
    valueRange= 10f..50f,
    onValueChange = {
        value1 = it
    },
)
```
![screenshot2][2]
```kotlin
OldSlider(
    value = value2,
    valueRange= 100f..900f,
    steps = 9,
    onValueChange = {
        value2 = it
    },
)
```
[1]: https://raw.githubusercontent.com/joelnah/old-slider/master/screenshot1.png
[2]: https://raw.githubusercontent.com/joelnah/old-slider/master/screenshot2.png