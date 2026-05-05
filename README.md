# Screen-Time-Control
Smart Screen Time Control helps you break phone addiction by automatically blocking apps when you've used them too much. Track usage, enforce limits, and stay focused—all offline with a simple, clean interface.

```markdown
# Smart Screen Time Control App 📱⏰

<div align="center">

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)
![API](https://img.shields.io/badge/API-26%2B-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)

**A beginner-to-intermediate Android app that helps users reduce screen addiction by monitoring app usage, enforcing time limits, and blocking selected apps.**

[Features](#features) • [Installation](#installation) • [How It Works](#how-it-works) • [Setup](#setup) • [Contributing](#contributing)

</div>

---

## 📋 Overview

Smart Screen Time Control is a lightweight Android application built with **Kotlin** that promotes digital wellbeing by:
- ⏱️ Tracking real-time app usage
- 🚫 Blocking apps when time limits are exceeded
- ⏳ Enforcing cooldown periods to prevent reopening
- 💾 Using local storage (no internet required)

Perfect for students, professionals, or anyone looking to build healthier smartphone habits.

---

## ✨ Features

### Core Functionality
- ✅ **Real-time App Monitoring** - Detects when monitored apps are opened using Accessibility Service
- ✅ **Time Limit Enforcement** - Default 10-minute session limit (customizable)
- ✅ **Full-Screen Blocking Overlay** - Prevents interaction when limits are exceeded
- ✅ **30-Minute Cooldown** - Restricts app reopening after blocking
- ✅ **Persistent Session Tracking** - Remembers usage across app restarts
- ✅ **No Third-Party Dependencies** - Pure Kotlin/Android SDK implementation
- ✅ **Offline-First** - All data stored locally using SharedPreferences

### Technical Highlights
- 🏗️ **Clean Architecture** - Separated concerns with dedicated managers
- 🔒 **Permission Handling** - Guided setup for required Android permissions
- 📊 **Accurate Time Tracking** - Millisecond-precision session management
- 🛡️ **Crash-Safe** - Null-safe Kotlin with defensive coding practices

---

## 🚀 Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android device with **API 26 (Android 8.0)** or higher
- Physical device recommended (emulators may have limited accessibility features)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/smart-screen-time-control.git
   cd smart-screen-time-control
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select `File > Open`
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device**
   - Connect your Android device via USB
   - Enable USB Debugging in Developer Options
   - Click `Run` in Android Studio

---

## ⚙️ Setup

### Required Permissions

The app needs three critical permissions to function:

#### 1. **Draw Over Other Apps** (Overlay Permission)
- Allows the app to display blocking screen
- Settings → Apps → Special Access → Display over other apps

#### 2. **Usage Access** (App Usage Stats)
- Enables tracking of foreground apps
- Settings → Security → Apps with usage access

#### 3. **Accessibility Service**
- Detects when monitored apps are opened
- Settings → Accessibility → Smart Screen Time → Enable

### First-Time Setup Flow

```
1. Install app
2. Open app and tap "Enable Required Permissions"
3. Follow system prompts to enable each permission
4. Return to app and verify status
5. Open monitored app (Instagram) to test
```

---

## 🔧 How It Works

### Architecture Overview

```
┌─────────────────────────────────────────────────────┐
│                   MainActivity                       │
│              (Permission Handling)                   │
└─────────────────┬───────────────────────────────────┘
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────▼─────────┐  ┌───────▼──────────┐
│ AppUsageService │  │ BlockOverlayService│
│  (Accessibility) │  │   (Foreground)    │
└───────┬─────────┘  └──────────────────┘
        │
    ┌───▼────┐
    │ Managers │
    ├────────┤
    │ PrefsManager  │ → SharedPreferences
    │ TimeManager   │ → Session Logic
    └──────────────┘
```

### Key Components

| Component | Responsibility |
|-----------|---------------|
| **MainActivity.kt** | UI, permission requests, status display |
| **AppUsageService.kt** | Foreground app detection via Accessibility |
| **BlockOverlayService.kt** | Full-screen blocking overlay management |
| **PrefsManager.kt** | Local storage wrapper (SharedPreferences) |
| **TimeManager.kt** | Session timing and cooldown logic |

---

## 📂 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/smartscreentime/
│   │   ├── MainActivity.kt              # Entry point & permissions
│   │   ├── AppUsageService.kt           # Accessibility service
│   │   ├── BlockOverlayService.kt       # Overlay display
│   │   ├── PrefsManager.kt              # Data persistence
│   │   └── TimeManager.kt               # Time calculations
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml        # Main UI
│   │   │   └── overlay_block.xml        # Block screen UI
│   │   ├── values/
│   │   │   └── strings.xml
│   │   └── xml/
│   │       └── accessibility_config.xml # Service config
│   └── AndroidManifest.xml              # App configuration
└── build.gradle
```

---

## 🎯 Configuration

### Customize Time Limits

Edit `PrefsManager.kt`:

```kotlin
companion object {
    const val DEFAULT_TIME_LIMIT = 10  // Change to desired minutes
    const val DEFAULT_COOLDOWN = 30    // Change cooldown duration
}
```

### Monitor Different Apps

Edit `AppUsageService.kt`:

```kotlin
private val monitoredPackage = "com.instagram.android"
// Change to:
// "com.twitter.android"       // Twitter
// "com.facebook.katana"       // Facebook
// "com.snapchat.android"      // Snapchat
// "com.zhiliaoapp.musically"  // TikTok
```

### Testing (Quick Limit)

For faster testing, temporarily set:

```kotlin
const val DEFAULT_TIME_LIMIT = 1  // 1 minute for testing
```

---

## 🧪 Testing

### Manual Testing Steps

1. **Install and grant permissions**
2. **Modify time limit to 1 minute** (optional)
3. **Open Instagram**
4. **Wait for limit**
5. **Verify overlay appears**
6. **Press OK and try reopening**
7. **Verify cooldown blocks immediate access**

### Expected Behavior

| Action | Expected Result |
|--------|----------------|
| First Instagram open | Session starts, timer begins |
| Use for 10 minutes | Overlay blocks app |
| Press OK | Overlay dismisses |
| Reopen immediately | Overlay blocks again (cooldown) |
| Wait 30 minutes | Instagram accessible again |

---

## 🛠️ Technologies Used

- **Language**: Kotlin 1.9+
- **Min SDK**: API 26 (Android 8.0 Oreo)
- **Target SDK**: API 34 (Android 14)
- **Architecture**: Service-based with manager pattern
- **Storage**: SharedPreferences
- **Services**: 
  - AccessibilityService (app detection)
  - Foreground Service (overlay)

---

## 🔮 Future Enhancements

Planned features for upcoming versions:

- [ ] **Multi-app selection UI** - Choose which apps to monitor
- [ ] **Custom time limits per app** - Different limits for different apps
- [ ] **Usage analytics dashboard** - Daily/weekly usage graphs
- [ ] **Focus mode scheduling** - Auto-block during study/work hours
- [ ] **Break reminders** - Periodic notifications
- [ ] **Export usage data** - CSV export for analysis
- [ ] **AI-based predictions** - Predict and prevent binge usage
- [ ] **Focus score system** - Gamification with achievements

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Contribution Guidelines

- Follow existing code style
- Add comments for complex logic
- Test on real devices before submitting
- Update README if adding new features

---

## 🐛 Known Issues

- **Accessibility Service** may stop on some devices after system updates (user needs to re-enable)
- **Overlay** may not block on some custom Android skins (MIUI, ColorOS) due to additional restrictions
- **Session tracking** resets if service is killed by system (low memory)

### Workarounds

- Keep app in "Do Not Optimize" list in battery settings
- Grant all permissions immediately after installation

---
