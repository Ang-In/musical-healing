# 🎵 Musical Healing - Android App

An Android application that helps users find music based on their current mood using the Jamendo API and ExoPlayer.

## Overview

Musical Healing is an Android app that:
- Allows users to input their current mood (happy, sad, calm, angry, etc.)
- Fetches relevant music tracks from Jamendo's free music library
- Plays a curated playlist using Media3 ExoPlayer
- Provides a beautiful, modern UI using Jetpack Compose

## Features

- **Mood-Based Music Discovery**: Enter your mood and get a personalized playlist
- **Free Music**: Uses Jamendo's Creative Commons licensed music library
- **Modern UI**: Built with Jetpack Compose and Material 3
- **Seamless Playback**: Powered by Media3 ExoPlayer
- **Multiple Mood Support**: Happy, Sad, Calm, Angry, and more

## Prerequisites

Before building this project, ensure you have:

1. **Java Development Kit (JDK)**
   - JDK 11 or higher is required
   - You can download from: https://adoptium.net/
   - Or use Android Studio's bundled JDK

2. **Android Studio**
   - Download from: https://developer.android.com/studio
   - Recommended: Latest stable version (Hedgehog or newer)

3. **Android SDK**
   - compileSdk: 35
   - minSdk: 24
   - targetSdk: 35

4. **Jamendo API Client ID**
   - Sign up at: https://devportal.jamendo.com/
   - Create a new application to get your client ID
   - Free tier available for personal use

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/musical-healing.git
cd musical-healing
```

### 2. Install Prerequisites

**Android Studio (Required)**
- Download from: https://developer.android.com/studio
- Install the latest stable version (Hedgehog or newer)
- Android Studio includes JDK, so you don't need to install Java separately

**Note**: If you want to build from command line without Android Studio, you'll need JDK 11 or higher:
```bash
# macOS (using Homebrew)
brew install openjdk@11

# Verify installation
java -version
# Should show: openjdk version "11.x.x" or higher
```

### 3. Configure Jamendo API Key

1. Sign up for a free Jamendo API key at: https://devportal.jamendo.com/
2. Create a new application to get your client ID
3. Open `app/src/main/java/com/example/musicalhealing/PlayerActivity.java`
4. Find line 35: `private static final String JAMENDO_CLIENT_ID = "YOUR_JAMENDO_CLIENT_ID";`
5. Replace `YOUR_JAMENDO_CLIENT_ID` with your actual Jamendo client ID

### 4. Open Project in Android Studio

1. Launch Android Studio
2. Click "Open" and select the `musical-healing` directory
3. Wait for Gradle sync to complete (this may take a few minutes on first run)
4. If prompted to update Gradle or plugins, click "Update"

### 5. Build the Project

**Option A: Using Android Studio (Recommended)**
1. Click "Build" → "Make Project" or press Cmd+F9 (Mac) / Ctrl+F9 (Windows)
2. Wait for the build to complete successfully

**Option B: Using Command Line**
```bash
./gradlew clean build
```

### 6. Run the App

**Using Android Studio:**
1. Connect an Android device via USB (with USB debugging enabled) OR start an Android emulator
   - To create an emulator: Tools → Device Manager → Create Device
   - Recommended: Pixel 5 or newer with API 24+
2. Click the "Run" button (green play icon) or press Cmd+R (Mac) / Shift+F10 (Windows)
3. Select your device/emulator
4. Wait for the app to install and launch

**Using Command Line:**
```bash
# Install on connected device or running emulator
./gradlew installDebug

# Launch the app
adb shell am start -n com.example.musicalhealing/.MainActivity
```

## Project Structure

```
musical-healing/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/musicalhealing/
│   │   │   │   ├── MainActivity.kt          # Welcome screen (Compose)
│   │   │   │   ├── SearchActivity.java      # Mood input screen
│   │   │   │   ├── PlayerActivity.java      # Music player screen
│   │   │   │   ├── JamendoApi.java         # API interface
│   │   │   │   ├── JamendoResp.java        # API response model
│   │   │   │   ├── JamendoTrack.java       # Track data model
│   │   │   │   ├── ServiceLocator.java     # Dependency injection
│   │   │   │   ├── MoodUtils.java          # Mood query expansion
│   │   │   │   ├── PlaylistRepository.java # Playlist fetching logic
│   │   │   │   └── ui/theme/               # Compose theme files
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_player.xml
│   │   │   │   │   └── activity_search.xml
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── .gitignore
```

## Key Components

### Activities

- **MainActivity**: Entry point with welcome screen (Jetpack Compose)
- **SearchActivity**: Mood input screen (XML layout)
- **PlayerActivity**: Music player with ExoPlayer (XML layout)

### API Integration

- **JamendoApi**: Retrofit interface for Jamendo API
- **ServiceLocator**: Singleton pattern for API instance
- **JamendoTrack**: Data model for music tracks

### Utilities

- **MoodUtils**: Expands mood queries with related keywords
- **PlaylistRepository**: Handles playlist fetching logic

## Dependencies

- **Jetpack Compose** - Modern UI toolkit
- **Material3** - Material Design components
- **Media3 ExoPlayer** - Audio playback
- **Retrofit** - REST API client
- **Moshi** - JSON parsing
- **Coil** - Image loading

## Usage

1. Launch the app
2. Tap "Get Started" on the welcome screen
3. Enter your current mood (e.g., "happy", "calm", "sad")
4. Tap "Generate Playlist"
5. Enjoy your mood-based music playlist!

## Supported Moods

- **Happy**: Uplifting, feel-good, upbeat, cheerful music
- **Sad**: Melancholic, somber, emotional music
- **Calm**: Ambient, chill, relaxing, lofi music
- **Angry**: Intense, heavy, energetic music
- **Custom**: Enter any mood keyword

## Troubleshooting

### Build Fails with "Unable to locate a Java Runtime"
- Install JDK 11 or higher (see Setup Instructions above)
- Set JAVA_HOME environment variable
- Restart your terminal/IDE

### No tracks found
- Verify your Jamendo API client ID is correct
- Check internet connection
- Try a different mood keyword

### Playback issues
- Ensure INTERNET permission is granted
- Check device volume
- Try different tracks

## License

This project is open source. 

**Music License**: All music is sourced from Jamendo and is licensed under Creative Commons. Please respect artist licenses and provide attribution when required.

## Contributing

Feel free to submit issues and pull requests!

## Support

For issues or questions:
- Check the troubleshooting section
- Review Jamendo API docs: https://developer.jamendo.com/
- Check ExoPlayer docs: https://developer.android.com/media/media3

## Credits

- Music provided by [Jamendo](https://www.jamendo.com/)
- Built with [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Audio playback by [Media3 ExoPlayer](https://developer.android.com/media/media3)

---

Made with ❤️ for music lovers

