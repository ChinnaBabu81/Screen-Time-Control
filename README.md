
# Screen-Time-Control
Smart Screen Time Control helps you break phone addiction by automatically blocking apps when you've used them too much. Track usage, enforce limits, and stay focused—all offline with a simple, clean interface.
> **An Android application that helps users reduce screen addiction by monitoring app usage, enforcing time limits, and blocking selected apps.**

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
