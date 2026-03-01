# Todo Application (Android Compose)

A simple, modern Todo application built with Jetpack Compose, Kotlin, and Room, demonstrating clean architecture, state management, and adaptive UI design.

## Features

- Add, update, delete Todos – Simple CRUD operations with persistent storage using Room.
- Mark as complete/incomplete – Todos can be toggled with a checkbox.
- Filter Todos – View All, Completed, or Incomplete Todos with a segmented button filter.
- Swipe-to-delete – Delete a Todo via intuitive left-to-right swipe.
- Adaptive UI – Layout responds to window size and device orientation.
- Material 3 Design – Leverages Material 3 components and theming.
- Date display – Shows current date in a clean, readable format.
- Unidirectional data flow – Implemented using StateFlow in the ViewModel for predictable UI updates.

## Tech Stack

- Language: Kotlin
- UI Framework: Jetpack Compose
- State Management: MVVM / StateFlow
- Persistence: Room Database
- Icons & Drawables: Vector resources
- Minimum SDK: 33+

## Architecture

This app uses MVVM with unidirectional state flow, separating:

- UI (Composable Screens) – Stateless, reactive to state.
- ViewModel – Exposes a single StateFlow for the UI and handles all user intents.
- Repository – Abstracts database access via Room.

This approach ensures:

- Predictable UI behavior
- Single source of truth
- Easy testing and maintenance

## Installation

1.	Clone the repository:

```bash
git clone https://github.com/ahmed-cmyk/android_todo.git
```

2. Open in Android Studio (Electric Eel or newer recommended).
3. ld and run on a device or emulator.

## Future Improvements

- Push notifications for upcoming tasks (FCM integration).
- Undo delete with Snackbar.
- Adaptive layouts for tablets and foldables using window size classes.
- KMM (Kotlin Multiplatform) support for iOS version.
- Enhanced UI animations and Material 3 theming.

## License

This project is open-source and available under the MIT License.

---