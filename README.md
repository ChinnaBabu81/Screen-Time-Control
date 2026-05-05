
# Screen-Time-Control
Smart Screen Time Control helps you break phone addiction by automatically blocking apps when you've used them too much. Track usage, enforce limits, and stay focused—all offline with a simple, clean interface.
> **An Android application that helps users reduce screen addiction by monitoring app usage, enforcing time limits, and blocking selected apps.**

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Permissions](#permissions)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## 🌟 Overview

**Smart Screen Time Control** is a productivity-focused Android app designed to combat smartphone addiction by:

- Monitoring time spent on specific apps (e.g., Instagram)
- Enforcing configurable time limits
- Blocking apps with a full-screen overlay when limits are exceeded
- Implementing cooldown periods to prevent immediate reopening

This is a **Version 1** implementation focusing on core functionality without third-party dependencies.

---

## ✨ Features

### Core Functionality

✅ **Real-Time App Monitoring**
- Uses AccessibilityService to detect when monitored apps are opened
- Tracks session duration automatically

✅ **Time Limit Enforcement**
- Default 10-minute usage session
- Automatic blocking when limit is exceeded

✅ **Smart Cooldown System**
- 30-minute cooldown period after blocking
- Prevents immediate app reopening

✅ **Full-Screen Blocking Overlay**
- Displays motivational message
- Shows remaining cooldown time
- Provides quick access to home screen

✅ **Persistent Data Storage**
- Uses SharedPreferences for configuration
- Stores time limits, cooldown periods, and session data

✅ **Permission Management**
- Guides users through enabling required permissions
- Real-time permission status display

---
# Smart Screen Time Control App - Version 1

I'll provide you with a complete working Android application that monitors and controls screen time. Let's build this step by step.

## 📁 Project Structure

```
SmartScreenTimeControl/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/smartscreentime/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── AppUsageService.kt
│   │   │   │   ├── BlockOverlayService.kt
│   │   │   │   ├── PrefsManager.kt
│   │   │   │   └── TimeManager.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── overlay_block.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── colors.xml
│   │   │   │   └── xml/
│   │   │   │       └── accessibility_service_config.xml
│   │   │   └── AndroidManifest.xml
```

---
## 🧪 Testing Instructions

### On Real Android Device:

1. **Install the app**
2. **Open the app** - you'll see the main screen with permission statuses
3. **Click "Enable All Permissions"** button and grant:
   - Usage Access Permission
   - Draw Over Apps Permission
   - Accessibility Service Permission
4. **Return to the app** - all permissions should show ✅
5. **Open Instagram** (must be installed)
6. **Wait 10 minutes** OR modify `DEFAULT_TIME_LIMIT` to 1 minute for testing
7. **Verify** the block overlay appears
8. **Try reopening Instagram** - should be blocked for 30 minutes

### Quick Test Mode:
In `PrefsManager.kt`, change:
```kotlin
const val DEFAULT_TIME_LIMIT = 1 // 1 minute instead of 10
const val DEFAULT_COOLDOWN = 2 // 2 minutes instead of 30
```

---

## 🎯 Key Features Implemented

✅ Permission handling (Usage Access, Overlay, Accessibility)  
✅ Real-time app monitoring using AccessibilityService  
✅ 10-minute session time limit  
✅ 30-minute cooldown after blocking  
✅ Full-screen blocking overlay  
✅ Persistent storage using SharedPreferences  
✅ Automatic home navigation when blocked  
✅ Real-time cooldown timer display  

---

## 📝 Important Notes

- **Must test on a real device** (not emulator for full functionality)
- Instagram app must be installed
- All three permissions are mandatory
- Service runs in background continuously
- Overlay cannot be dismissed until cooldown ends

---

## 🐛 Troubleshooting

**Service not working?**
- Check all permissions are granted
- Restart the device after granting permissions
- Verify Accessibility Service is ON in Settings

**Overlay not showing?**
- Ensure "Draw Over Apps" permission is granted
- Check Android battery optimization is disabled for the app

**Time limit not working?**
- Verify accessibility service is running
- Check Logcat for "AppUsageService" tags

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Permissions](#permissions)
- [Tech Stack](#tech-stack)

---

## 🌟 Overview

**Smart Screen Time Control** is a productivity-focused Android app designed to combat smartphone addiction by:

- Monitoring time spent on specific apps (e.g., Instagram)
- Enforcing configurable time limits
- Blocking apps with a full-screen overlay when limits are exceeded
- Implementing cooldown periods to prevent immediate reopening

This is a **Version 1** implementation focusing on core functionality without third-party dependencies.

---

## ✨ Features

### Core Functionality

✅ **Real-Time App Monitoring**
- Uses AccessibilityService to detect when monitored apps are opened
- Tracks session duration automatically

✅ **Time Limit Enforcement**
- Default 10-minute usage session
- Automatic blocking when limit is exceeded

✅ **Smart Cooldown System**
- 30-minute cooldown period after blocking
- Prevents immediate app reopening

✅ **Full-Screen Blocking Overlay**
- Displays motivational message
- Shows remaining cooldown time
- Provides quick access to home screen

✅ **Persistent Data Storage**
- Uses SharedPreferences for configuration
- Stores time limits, cooldown periods, and session data

✅ **Permission Management**
- Guides users through enabling required permissions
- Real-time permission status display

---

## 🏗️ Architecture

### Components

```
┌─────────────────────────────────────────────┐
│          MainActivity.kt                    │
│  (Permission Requests & Status Display)     │
└────────────────┬────────────────────────────┘
                 │
                 ├──────────────────────────────┐
                 │                              │
    ┌────────────▼──────────┐     ┌─────────────▼──────────┐
    │  AppUsageService.kt   │     │ BlockOverlayService.kt │
    │ (Monitor Foreground)  │────▶│  (Display Blocker)     │
    └────────────┬──────────┘     └─────────────┬──────────┘
                 │                              │
                 └──────────┬───────────────────┘
                            │
              ┌─────────────▼────────────┐
              │   TimeManager.kt         │
              │ (Cooldown & Limit Logic) │
              └─────────────┬────────────┘
                            │
              ┌─────────────▼────────────┐
              │   PrefsManager.kt        │
              │ (Persistent Storage)     │
              └──────────────────────────┘
```

### Design Pattern
- **Separation of Concerns**: Each component handles specific functionality
- **Manager Pattern**: TimeManager and PrefsManager encapsulate business logic
- **Service-Oriented**: Background monitoring using Android Services

---

## 🚀 Installation

### Prerequisites

- Android Studio (Electric Eel or newer)
- Android device with API 26+ (Android 8.0 Oreo or higher)
- Kotlin 1.8+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/smart-screen-time-control.git
   cd smart-screen-time-control
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device**
   - Connect your Android device via USB
   - Enable USB Debugging
   - Click Run ▶️ in Android Studio

---

## 📖 Usage

### First-Time Setup

1. **Launch the app**
2. **Grant permissions** by clicking "Enable All Permissions"
   - Usage Access Permission
   - Draw Over Apps Permission
   - Accessibility Service Permission
3. **Verify** all permissions show ✅ (green checkmarks)

### Monitoring Apps

Currently monitors **Instagram** by default:
- **Package**: `com.instagram.android`
- **Time Limit**: 10 minutes
- **Cooldown**: 30 minutes

### Testing

To test quickly, modify time limits in `PrefsManager.kt`:

```kotlin
const val DEFAULT_TIME_LIMIT = 1 // 1 minute
const val DEFAULT_COOLDOWN = 2 // 2 minutes
```

### Expected Behavior

1. Open Instagram
2. Use for the configured time limit
3. Block overlay appears automatically
4. App remains blocked during cooldown period
5. Overlay disappears when cooldown expires

---

## 🔐 Permissions

### Required Permissions

| Permission | Purpose | Mandatory |
|-----------|---------|-----------|
| **Usage Access** | Track app usage statistics | ✅ Yes |
| **Draw Over Apps** | Display blocking overlay | ✅ Yes |
| **Accessibility Service** | Detect foreground app changes | ✅ Yes |

### Privacy Note

- **No data is collected or transmitted**
- All data stored locally using SharedPreferences
- No internet permission required
- No analytics or tracking

---

## 🛠️ Tech Stack

### Language
- **Kotlin** 100%

### Android Components
- AccessibilityService
- WindowManager (Overlay)
- SharedPreferences
- Intent System

### UI/UX
- Material Design 3
- CardView
- ConstraintLayout

### Minimum Requirements
- **Min SDK**: API 26 (Android 8.0)
- **Target SDK**: API 34 (Android 14)

---
