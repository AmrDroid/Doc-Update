# DocUpdate -- Mobile Engineer Take-Home

**Platform:** Android (Kotlin + Jetpack Compose)

This project implements a mobile-first clinician workflow for reviewing
engagement messages, inspecting compliance details, and running
compliance checks. The focus of this implementation is strong mobile
architecture, clean separation of concerns, offline-first behavior, and
testable business logic.

------------------------------------------------------------------------

# 📱 Features

## Message List Screen

-   Displays engagement messages
-   Each row shows:
    -   Topic
    -   Physician name
    -   Sentiment
    -   Delivery status
    -   Timestamp
-   Supports filtering:
    -   By physician
    -   By date range (Last 7 days, Last 30 days, Last 365 days, All)

## Message Detail Screen

-   Displays:
    -   Full message text
    -   Sentiment
    -   Compliance tag
-   Includes:
    -   **Run Compliance Check** button

## Compliance Classification

-   Keyword-based rule evaluation using `compliance_policies.json`
-   Displays:
    -   Triggered rule(s)
    -   Why each rule was triggered

------------------------------------------------------------------------

# 🚀 Setup Instructions

## 1. Clone Repository

git clone `https://github.com/AmrDroid/Doc-Update` cd docupdate

## 2. Open in Android Studio

-   Android Studio Hedgehog or newer recommended
-   Kotlin 1.9+
-   Compile SDK 34

## 3. Dependencies

Ensure these dependencies exist in `app/build.gradle`:

implementation("androidx.datastore:datastore-preferences:1.0.0")
implementation("com.opencsv:opencsv:5.8")

implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
implementation("com.google.dagger:hilt-android:2.48")
kapt("com.google.dagger:hilt-android-compiler:2.48")

testImplementation("junit:junit:4.13.2")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

Sync Gradle after adding.

## 4. Data Files

Place the provided files inside:

app/src/main/assets/

-   messages.csv
-   physicians.csv
-   compliance_policies.json

## 5. Run the App

Select an emulator/device and click Run.

------------------------------------------------------------------------

# 🏗 Architecture Overview

The project follows a Clean Architecture--inspired structure with clear
separation of concerns:

presentation/ message_list/ message_detail/

domain/ model/ compliance/ analytics/

data/ repository/ local/ analytics/

di/

------------------------------------------------------------------------

## Presentation Layer

-   Built with Jetpack Compose
-   ViewModels use StateFlow
-   UI state modeled as immutable data classes
-   No business logic inside composables

## Domain Layer

-   Pure Kotlin
-   ComplianceEngine
-   ComplianceQueueStorage interface
-   Use cases for fetching data
-   No Android framework dependency

## Data Layer

-   CSV parsing via OpenCSV
-   DataStore for offline queue persistence
-   Analytics implementation
-   Repository implementations

## Dependency Injection

-   Hilt used for:
    -   Repositories
    -   Analytics
    -   Queue storage
    -   Compliance engine

------------------------------------------------------------------------

# 🧠 State Management

-   StateFlow used for reactive UI updates
-   Immutable UiState
-   Filtering and mapping handled in ViewModel
-   Business logic separated from UI

------------------------------------------------------------------------

# 📦 Offline-First Behavior (Caching + Replay)

Compliance checks support offline-first behavior:

-   If online → compliance evaluated immediately
-   If offline → action is queued in DataStore
-   Queue persists across app restarts
-   Replay processes queued actions when online

This ensures clinician actions are never lost.

------------------------------------------------------------------------

# 🧪 Unit Tests

Unit tests cover:

-   Compliance rule matching
-   Case-insensitive keyword detection
-   Multiple rule triggering
-   Date range filtering
-   Offline queue enqueue + replay behavior

All tests are pure JVM tests (no Android framework dependency).

------------------------------------------------------------------------

# 📊 Basic Analytics

Analytics is abstracted via an AnalyticsTracker interface.

Event tracked: compliance_check_triggered

Properties: - message_id - physician_id - topic

Analytics implementation currently logs to Logcat but can easily be
replaced with Firebase or another provider.

------------------------------------------------------------------------

# ♿ Accessibility Considerations

-   Semantic grouping for screen readers
-   Descriptive content descriptions
-   Minimum 48dp touch targets
-   Scalable typography (respects system font size)
-   No reliance on color alone
-   Accessible loading and empty states

Designed with clinician usability in mind.

------------------------------------------------------------------------

# ⚖ Key Tradeoffs and Decisions

### CSV Parsing

Used OpenCSV instead of manual string splitting to properly handle
quoted fields and commas.

### State Modeling

Used data classes for screen state instead of sealed classes because
state is composable (loading + filters + data).

### Offline Queue via DataStore

Chose DataStore instead of SharedPreferences for: - Coroutine safety -
Non-blocking IO - Modern Android best practices

### Analytics at ViewModel Layer

Analytics events are triggered in ViewModel to avoid UI coupling.

### Business Logic in Domain

Compliance engine is framework-independent for maximum testability.

------------------------------------------------------------------------

# 🔧 What I Would Improve With More Time

-   Replace CSV with Room database
-   Add WorkManager for background replay
-   Implement real network monitoring
-   Add Compose UI tests
-   Add pagination for large datasets
-   Introduce richer error modeling
-   Improve tablet and large-screen support
-   Integrate real analytics provider
-   Add performance profiling for large data sets