---

**Context:** This is a Kotlin Multiplatform Compose project targeting Android, iOS, and JVM Desktop. It uses Coil 3.4.0 for image loading and Koin 4.2 for DI with platform-specific modules. Currently, `ArticleImage` composable creates its own `ImageLoader` inline. We need to refactor this into a proper shared multiplatform `ImageLoader` singleton managed by Koin.

---

**Task:** Perform the following changes across the project:

---

**1. Create a multiplatform `ImageLoader` factory in the `core` package**

Create `expect`/`actual` declarations under the `core` package in each source set.

In `commonMain/core/`, create `ImageLoaderFactory.kt`:
```kotlin
expect fun createImageLoader(context: PlatformContext): ImageLoader
```

In `androidMain/core/`, create `ImageLoaderFactory.android.kt` — actual builds `ImageLoader` using `OkHttpNetworkFetcherFactory` with:
- `protocols(listOf(Protocol.HTTP_1_1))`
- `readTimeout(20, TimeUnit.SECONDS)`
- `retryOnConnectionFailure(true)`

In `iosMain/core/`, create `ImageLoaderFactory.ios.kt` — actual builds `ImageLoader` using `NSURLSessionNetworkFetcherFactory` with `NSURLSession` configured:
- `timeoutIntervalForRequest = 20.0`
- `waitsForConnectivity = true`
- `HTTPAdditionalHeaders = mapOf("Connection" to "keep-alive")`

In `desktopMain/core/`, create `ImageLoaderFactory.desktop.kt` — actual builds `ImageLoader` using `KtorNetworkFetcherFactory` with `HttpClient(CIO)`:
- `install(HttpTimeout) { requestTimeoutMillis = 20_000; socketTimeoutMillis = 20_000 }`

All three actuals must also apply on `ImageLoader.Builder`:
- `.memoryCachePolicy(CachePolicy.ENABLED)`
- `.diskCachePolicy(CachePolicy.ENABLED)`

---

**2. Create a Koin module for `ImageLoader` in the `core` package**

In `commonMain/core/`, create `ImageLoaderModule.kt`:

```kotlin
val imageLoaderModule = module {
    single<ImageLoader> { createImageLoader(get<PlatformContext>()) }
}
```

`PlatformContext` must already be provided in the platform Koin module (see step 3). Do not register it here — only consume it via `get()`.

---

**3. Register `PlatformContext` in each platform Koin module**

Find the existing platform-specific Koin modules (e.g. `androidModule`, `iosModule`, `desktopModule`). In each one, add a `single` binding for `PlatformContext` if it is not already present:

- **Android**: `single<PlatformContext> { androidContext() }` — requires the Koin Android context, which is already available if `startKoin { androidContext(this@App) }` is used at app startup. Do not change the startup call, only add the binding if missing.
- **iOS**: `single<PlatformContext> { PlatformContext.INSTANCE }`
- **Desktop**: `single<PlatformContext> { PlatformContext.INSTANCE }`

---

**4. Include `imageLoaderModule` in the Koin setup**

Find where `startKoin` or `KoinApplication` is configured (typically in `App.kt`, `MainActivity.kt`, or a dedicated DI setup file). Add `imageLoaderModule` to the modules list:

```kotlin
startKoin {
    modules(
        imageLoaderModule,
        // ... existing modules
    )
}
```

Do not remove or reorder any existing modules.

---

**5. Remove `LocalImageLoader` composition local — use Koin injection instead**

If a `LocalImageLoader` composition local exists from a previous attempt, delete it. Do not use `CompositionLocalProvider` for `ImageLoader`.

Instead, in every composable that needs an `ImageLoader`, inject it via Koin:

```kotlin
val imageLoader: ImageLoader = koinInject()
```

---

**6. Update all composables that load images via Coil**

Search the entire project for all usages of:
- `rememberAsyncImagePainter(`
- `AsyncImage(`
- `SubcomposeAsyncImage(`

For each one, add at the top of the composable (if not already present):

```kotlin
val imageLoader: ImageLoader = koinInject()
```

And pass it as the `imageLoader` parameter. Example:

```kotlin
rememberAsyncImagePainter(
    model = url,
    imageLoader = imageLoader,
    ...
)
```

---

**Constraints:**
- All new files go under the `core` package in their respective source sets
- Do not change any UI logic, layout, or styling in any composable — only add/replace `imageLoader` wiring
- Do not introduce any new DI framework or composition locals — use Koin exclusively
- Keep all existing `onSuccess`, `onError`, `filterQuality`, and other Coil parameters intact
- `createImageLoader` is called only once — inside the Koin `single { }` block — never inside `remember` in a composable
- Do not modify `startKoin` startup parameters other than adding `imageLoaderModule` to the modules list