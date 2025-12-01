# Gboard Material Expressive Black

LSPosed module that restores pitch black backgrounds to Gboard on Android 16, bypassing Material 3 Expressive enforcement while preserving dynamic theme colors.

![GitHub Release](https://img.shields.io/github/v/release/hxreborn/gboard-material-expressive-black)
![GitHub Downloads](https://img.shields.io/github/downloads/hxreborn/gboard-material-expressive-black/total)
![Android API](https://img.shields.io/badge/API-31%2B-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue)
![APK Size](https://img.shields.io/badge/APK%20Size-~50KB-orange)

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

---

Notice the grey/tinted background (module off) vs pitch black (module on). Dynamic theme colors are preserved.

## Requirements

- Android 12+ (Android 16+ recommended for grey background fix)
- LSPosed framework

Tested on Pixel 9 Pro, Android 16 (BP3A.251105.015), JingMatrix LSPosed fork, KernelSU Next. Functionality on other configurations untested.

## Installation

1. Install [LSPosed](https://github.com/LSPosed/LSPosed/releases)
2. Download latest APK from [releases](https://github.com/hxreborn/gboard-material-expressive-black/releases)
3. Install APK and enable module in LSPosed Manager
4. Add **Gboard** to module scope
5. Force stop Gboard or reboot

## Building from source

```bash
git clone --recurse-submodules https://github.com/hxreborn/gboard-material-expressive-black.git
cd gboard-material-expressive-black
./gradlew assembleDebug
```

**Build requirements:** JDK 21, Gradle 8.7+

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
