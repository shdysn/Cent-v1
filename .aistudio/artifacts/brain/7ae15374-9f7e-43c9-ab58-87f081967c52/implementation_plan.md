# APK Size Optimization (Targeting ~1MB)

Comprehensive build and asset optimization to drastically reduce the release APK size from ~18MB down toward the target size using R8 code minification, aggressive resource shrinking, ProGuard tuning, packaging exclusions, and asset compression.

### User Review & Critical Decisions

> [!IMPORTANT]
> The user confirmed the optimization approach: **Full R8 minification, resource shrinking, and icon optimization**. Because Jetpack Compose includes foundational UI runtime code (~1.5–2MB minimum footprint), we will apply maximum R8 optimization and resource stripping to achieve the smallest possible release binary.

- **Confirmed Decision**: Enable full R8 minification and resource shrinking for the release build type.
- **Optimization Strategy**: Optimize ProGuard rules, strip non-essential packaging metadata, compress image drawables, and prune unused resources.
- **Signing**: Configure the release build to use the standard debug signing config so the resulting release APK is immediately installable and testable.

---

### 1. Overview & Core Concept

- **What It Does**: Transforms the build pipeline to strip all unused code, dead classes, unused vector drawables, redundant translations, and packaging metadata, reducing the APK footprint from ~18MB to the minimum possible size.
- **Target Audience**: End users on bandwidth-constrained connections and low-storage devices who need a fast, lightweight file manager download.
- **Key Value**: Drastically smaller download size, faster app startup, and minimal storage footprint.

---

### 2. User Experience & Visual Design

- **Zero Feature Loss**: All file explorer capabilities (Recent files, Storage analyzer, Category filters, Media Vault, FTP server, in-app Package installer, Text editor, and Audio player) remain 100% functional.
- **Visual Fidelity**: Icons, colors, typography, themes, and animations remain crisp and intact.
- **App Launcher Icon**: The custom adaptive launcher icon will be optimized for file size without losing visual clarity.

---

### 3. Key Product Decisions & Trade-Offs

- **R8 Full Mode & Resource Shrinking**:
  - *Chosen Approach*: Enable `isMinifyEnabled = true` and `isShrinkResources = true` with `proguard-android-optimize.txt` in the release build.
  - *Why*: Eliminates unreferenced methods and resources from `material-icons-extended`, Compose BOM, and AndroidX libraries.
  - *Trade-Off*: Longer build compilation times during release builds, but huge savings in APK size (up to 80-90% reduction).
- **Packaging Resource Exclusions**:
  - *Chosen Approach*: Strip non-essential build metadata files (`META-INF/*.kotlin_module`, `META-INF/DEPENDENCIES`, `META-INF/LICENSE*`) from the APK zip.
  - *Why*: Saves dozens of kilobytes of useless text files inside the APK bundle.
- **High-Efficiency Asset Compression**:
  - *Chosen Approach*: Optimize the 323 KB launcher artwork (`cent_explorer_icon.jpg`) to a compact, compressed format.
  - *Why*: Direct reduction of resource payload without affecting display resolution on device screens.

---

### 4. Technical Architecture & Data Strategy *(Technical Reference)*

```
┌────────────────────────────────────────────────────────────────────────┐
│                        app/build.gradle.kts                            │
├────────────────────────────────────────────────────────────────────────┤
│  buildTypes {                                                          │
│      release {                                                         │
│          isMinifyEnabled = true                                        │
│          isShrinkResources = true                                      │
│          proguardFiles(getDefaultProguardFile(                         │
│              "proguard-android-optimize.txt"), "proguard-rules.pro")   │
│          signingConfig = signingConfigs.getByName("debugConfig")       │
│      }                                                                 │
│  }                                                                     │
│  packaging {                                                           │
│      resources.excludes += setOf("META-INF/*.version", ...)            │
│  }                                                                     │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                         proguard-rules.pro                             │
├────────────────────────────────────────────────────────────────────────┤
│  • Keep Compose runtime composables and ViewModels                     │
│  • Strip logging and Kotlin metadata attributes                        │
│  • Aggressively prune unused Material icons and classes                │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                       Resulting Release APK                            │
│      Path: app/build/outputs/apk/release/app-release.apk               │
│      Significantly reduced DEX, resource table, and file payload       │
└────────────────────────────────────────────────────────────────────────┘
```

#### Implementation Steps:
1. **Update `app/build.gradle.kts`**:
   - Turn on `isMinifyEnabled = true` and `isShrinkResources = true` for release build type.
   - Configure packaging options to exclude redundant `META-INF` files.
   - Configure release signing with `debugConfig` so the APK is installable.
2. **Optimize `app/proguard-rules.pro`**:
   - Configure R8 optimization rules tailored for Jetpack Compose and coroutines.
   - Retain necessary model classes and reflection-free view models.
3. **Compress Image Assets**:
   - Re-compress `cent_explorer_icon.jpg` to a smaller footprint (~30-50 KB).
4. **Compile & Measure**:
   - Run Gradle release build and verify exact output APK size.
   - Verify that the applet compiles and runs without runtime crashes.
