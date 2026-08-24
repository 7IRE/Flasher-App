# Flasher ⚡️

Flasher is a modern Android flashlight utility app built with Jetpack Compose and Kotlin. Unlike standard flashlight apps, Flasher features a customizable strobe light with multiple speed intervals that continues to run seamlessly in the background, even when the app is minimized.

## ✨ Features

* **Variable Strobe Speeds:** Choose from a standard solid light (Speed 0) up to 6 different high-speed blinking intervals.
* **Background Execution:** Powered by an Android Foreground Service, the flashlight keeps blinking even if you close the UI or lock your screen.
* **Modern UI:** Built entirely in Jetpack Compose, featuring a sleek, dark-themed interface with custom glowing neon buttons.
* **Safe Concurrency:** Uses Kotlin Coroutines to manage exact delay intervals without freezing the main thread.
* **Adaptive Icon:** Features a custom neon lightning bolt design built with Android Vector Drawables.

## 🛠️ Tech Stack

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Architecture:** MVVM (Model-View-ViewModel)
* **Concurrency:** Kotlin Coroutines (`serviceScope`, `delay`)
* **Background Work:** Android Foreground Services (`FOREGROUND_SERVICE_TYPE_CAMERA`)
* **Hardware Interoperability:** CameraManager API

## 🔒 Permissions Required

To function correctly on modern Android devices (Android 13/14+), this app requires the following permissions:
* `CAMERA` (To access the physical LED flash hardware)
* `FOREGROUND_SERVICE` & `FOREGROUND_SERVICE_CAMERA` (To keep the loop running when the app is minimized)
* `POST_NOTIFICATIONS` (To show the mandatory background service notification)

## 🚀 How to Run

1. Clone this repository to your local machine.
2. Open the project in **Android Studio**.
3. Sync the Gradle files.
4. Connect a physical Android device (Emulators do not have a physical camera flash and may crash).
5. Build and run the app. 
6. *Note: On first launch on Android 13+, you will need to manually accept the Camera and Notification permissions.*

---
*Built to explore Jetpack Compose state management and Android 14 Service restrictions.*
