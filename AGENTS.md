# AGENTS.md — SpaceflightNews (SpaceNews Explorer)

## Project
- Compose Multiplatform app (Android, JVM desktop, iOS arm64 + simulator)
- Single Gradle module: `:composeApp`
- Package: `ru.alexmaryin`
- Kotlin **2.2.10**, Compose Multiplatform **1.9.0**, AGP **8.12.0**

## Commands
```bash
./gradlew :composeApp:assembleDebug        # build Android APK
./gradlew :composeApp:run                  # run JVM desktop app
./gradlew :composeApp:packageDistributionForCurrentOS  # desktop installer
```
- Gradle config cache and build cache are enabled (`gradle.properties`).
- JVM heap for Kotlin daemon and Gradle: `-Xmx4g`.

## Architecture (all under `composeApp/src/commonMain/kotlin/ru/alexmaryin/`)
| Directory | Purpose |
|---|---|
| `app/` | Root `App.kt`, `Navigation.kt`, Drawer, DataStore preferences |
| `core/` | Shared domain types (`Result`, `DataError`, `Error`), UI primitives (Theme, colors, screens), HttpClient factory |
| `news/` | Feature module: data (DTOs, Room DB, mappers, remote API, repository), domain (models, `SpaceNewsRepository`), ui (news list, article details, about screens) |
| `di/` | Koin DI — `init.kt` starts DI, `modules.kt` declares common modules |

- **Search & favourites**: `news/ui/news_list/` implements full-text search and local favorites persistence using Room. `SpaceNewsRepository` interface in `news/domain/` defines the contract.

- **DI**: Koin (BOM). Platform-specific modules live alongside their common counterparts (e.g., `di/modules.desktop.kt`).
- **Networking**: Ktor 3.3.0, fetches from [Spaceflight News API](https://thespacedevs.com/snapi). Uses CIO engine on common, Darwin on iOS.
- **Local DB**: Room 2.8.1 with KSP. DB schema directory: `composeApp/schemas`. DAO/entities in `news/data/local_api/database/`.
- **Paging**: androidx Paging (cash compose variant `3.3.0-alpha02-0.5.1`). Custom `KtorPagingSource` in `news/data/remote_api/`.

## Platform entrypoints
- **JVM desktop**: `composeApp/src/jvmMain/kotlin/ru/alexmaryin/main.kt` → `ru.alexmaryin.MainKt`
- **Android**: `composeApp/src/androidMain/AndroidManifest.xml`
- **iOS**: `iosApp/` Xcode project (consumes shared KMP framework)

## Gotchas
- **No test suite exists** — any new code should include tests.
- **Release signing** uses the debug signing config (`isMinifyEnabled = true`, `isShrinkResources = true`). Not production-ready signing.
- `-Xexpect-actual-classes` compiler flag is set. Expect/actual pattern is used for platform-specific implementations (e.g., DataStore, screen utils, DateTime).
- **iOS screen size**: `ScreenSizeInfo` on iOS converts pixels to dp by **dividing** by density (`px / density`). Multiplying produces incorrect widths that break compact layout detection.
- No CI, no pre-commit hooks, no lint/typecheck scripts beyond Gradle's built-in checks.
