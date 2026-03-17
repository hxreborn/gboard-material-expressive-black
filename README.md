# Gboard Material Expressive Black

LSPosed module that restores pitch black backgrounds to Gboard on Android 16, bypassing Material 3 Expressive enforcement while preserving dynamic theme colors.

![GitHub Release](https://img.shields.io/github/v/release/hxreborn/gboard-material-expressive-black)
![GitHub Downloads](https://img.shields.io/github/downloads/hxreborn/gboard-material-expressive-black/total?label=GitHub%20downloads)
![Xposed repo downloads](https://img.shields.io/github/downloads/Xposed-Modules-Repo/eu.hxreborn.gboardmaterialexpressiveblack/total?label=Xposed%20repo%20downloads)
![Android API](https://img.shields.io/badge/API-31%2B-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3.10-blue)
![APK Size](https://img.shields.io/badge/APK%20Size-~25KB-orange)

## Why This Module?

Starting with Android 16 and Material 3 Expressive, Gboard enforces tinted grey surface colors instead of pitch black. This design change breaks AMOLED power savings and prevents pure black themes.


This module intercepts Material You color lookups at the system level using LSPosed hooks, replacing surface container colors with pitch black while preserving wallpaper-derived dynamic theme colors.

## Visual Comparison

**Module off**

<img src="screenshots/stock_borders_off.png" alt="Stock without borders" width="100%"/>

**Module on**

<img src="screenshots/module_borders_off.png" alt="Module without borders" width="100%"/>

<details>
<summary>With key borders on</summary>

**Module off**

<img src="screenshots/stock_borders_on.png" alt="Stock with borders" width="100%"/>

**Module on**

<img src="screenshots/module_borders_on.png" alt="Module with borders" width="100%"/>

</details>


## Requirements

- Android 12+ (Android 16+ recommended for grey background fix)
- [LSPosed](https://github.com/LSPosed/LSPosed) with API 101 support (may not be publicly available yet)

Tested on Pixel 9 Pro, Android 16 (BP3A.251105.015), JingMatrix LSPosed fork, KernelSU Next. Functionality on other configurations untested.

## Installation

1. Install [LSPosed](https://github.com/LSPosed/LSPosed) with API 101 support
2. Download latest APK from [releases](https://github.com/hxreborn/gboard-material-expressive-black/releases)
3. Install APK and enable module in LSPosed Manager
4. Add **Gboard** to module scope
5. Force stop Gboard
6. Open any text field to trigger Gboard

## Building from source

```bash
git clone https://github.com/hxreborn/gboard-material-expressive-black.git
cd gboard-material-expressive-black
./gradlew assembleDebug
```

**Build requirements:** JDK 21, Gradle 8.7+

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
