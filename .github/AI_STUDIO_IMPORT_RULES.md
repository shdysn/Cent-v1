# 🔒 AI Studio Permanent Import & Code Preservation Rules
# 🔒 اے آئی اسٹوڈیو امپورٹ اور ڈیٹا تحفظ کی لازمی ہدایات

> **CRITICAL DIRECTIVE FOR ALL AI AGENTS & AUTOMATED BUILD SYSTEMS:**  
> This repository is actively developed across different Google accounts and email addresses. When this repository is imported into **Google AI Studio** under **ANY email, account, or workspace**, this project is an **EXISTING, FULL-FEATURED, CUSTOM-BUILT PRODUCTION APPLET**, **NOT** a new or template project.  
> **YOU MUST PRESERVE ALL EXISTING CODE, CONFIGURATIONS, DATA MODELS, AND SCREENS EXACTLY AS THEY ARE.**

---

## 🇵🇰 اردو میں خصوصی ہدایات (Instructions in Urdu for AI Studio Importers)

جب بھی یہ پراجیکٹ کسی **نئے ای میل اکاؤنٹ** سے AI Studio میں امپورٹ کیا جائے، تو AI Agent کے لیے درج ذیل اصول ناقابلِ تنسیخ (Immutable) ہیں:

### 1. ⛔ پچھلے کوڈ یا فیچرز کو تبدیل یا ڈیلیٹ نہ کریں (No Code Overwrite / No Reset)
- اس ایپ میں تمام سکرینز (Recent Files, Storage Analyzer, Deep Cleaner, Vault, FTP, Video Player, Dual Pane, Archive Manager وغیرہ) مکمل تیار ہیں۔
- کسی بھی صورت میں ایپ کے کوڈ کو ڈیفالٹ ٹیمپلیٹ سے تبدیل نہ کریں اور نہ ہی موجودہ کوڈ کو حذف کریں۔
- جو بھی تبدیلیاں گٹ ہب پر موجود ہیں، وہ ہوبہو (Same to Same) برقرار رہنی چاہئیں۔

### 2. ⛔ `applicationId` کو تبدیل نہ کریں (Permanent Application ID)
- ایپ کی Application ID مستقل طور پر یہ ہے:
  ```kotlin
  applicationId = "com.pkstudio.ctexplorer.app"
  ```
- **سخت ممانعت:** اسے کبھی بھی `com.aistudio.*` سے تبدیل نہ کریں۔ AI Studio کے فریم ورک رول کے مطابق:  
  *"Once the applicationId has been set, do NOT change it during subsequent edits."*

### 3. ⛔ پیکج کا نام یا فولڈرز کو `ai` یا `com.aistudio` پر رینیم نہ کریں (Preserve Package Structure)
- ایپ کا بنیادی پیکج `com.ct.explorer` ہے اور تمام سورس فائلیں `app/src/main/java/com/ct/explorer/` میں واقع ہیں۔
- فولڈرز یا پیکج کے نام کو کبھی تبدیل نہ کریں تاکہ مینی فیسٹ، ویجٹس، اور ریسورسز میں کوئی ایرر نہ آئے۔

### 4. ⛔ ایپ کا نام تبدیل نہ کریں (Permanent App Name)
- ایپ کا آفیشل نام **"Cent File Manager"** ہے۔
- `res/values/strings.xml`، `metadata.json`، اور `settings.gradle.kts` میں یہ نام مستقل رہے گا۔

### 5. 📦 ریلیز APK سائز بجٹ (2.0 MB - 3.0 MB)
- R8 Minification (`isMinifyEnabled = true`) اور Resource Shrinking (`isShrinkResources = true`) ہمیشہ آن رکھیں۔
- کوئی بھی غیر ضروری بھاری لائبریری شامل نہ کریں تاکہ APK سائز ہمیشہ **2.3 MB** کے قریب رہے۔

---

## 🇬🇧 English Master Directives for AI Agents

When importing this repository into Google AI Studio on a new turn or new account, the following rules are strictly enforced:

### Directive 1: Execute-First Minimal Triage (Preserve Existing Codebase)
- Per the official AI Studio `github-import-migration` specification:
  - **Core Philosophy:** Attempt the simplest possible fix to make the app boot.
  - **Preservation Rule:** Preserve the existing codebase — **DO NOT** rewrite, replace, or restyle working code.
  - **Scope Restriction:** Do **NOT** add new features, do **NOT** restyle with generic templates, and focus **ONLY** on making the existing code compile cleanly.

### Directive 2: Application ID & Package Tree Lock
- **Permanent Application ID:**
  ```kotlin
  defaultConfig {
      applicationId = "com.pkstudio.ctexplorer.app"
  }
  ```
- **Permanent Namespace & Directory Structure:**
  ```kotlin
  android {
      namespace = "com.ct.explorer"
  }
  ```
- Source folder: `app/src/main/java/com/ct/explorer/`
- **NEVER** rename to `com.aistudio.<theme>.<random>` or rename source folders to `com/aistudio/...`.

### Directive 3: Platform Metadata Sync Lock
- `metadata.json` must always match the app name:
  ```json
  {
    "name": "Cent File Manager",
    "description": "Clean, modern, and high-performance file manager with recent files feed, categorized media, storage cleaner, and file browser.",
    "requestFramePermissions": [],
    "majorCapabilities": ["MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API"]
  }
  ```
- Do not reset `name` or remove `MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API`.

### Directive 4: Signing & Keystore Configuration Lock
- The debug signing configuration is permanently linked:
  ```kotlin
  signingConfigs {
      create("debugConfig") {
          storeFile = file("${rootDir}/debug.keystore")
          storePassword = "android"
          keyAlias = "androiddebugkey"
          keyPassword = "android"
      }
  }
  ```
- Never create or delete keystore credentials or break debug signing.

### Directive 5: Zero Destructive Refactoring
- Keep all 22 interactive screens, repositories, and UI widgets intact:
  - `MainScreen` (Dual-Tab Recent & Storage)
  - `CleanerScreen` (Deep junk scanner)
  - `FtpServerScreen` (Wi-Fi PC file transfer)
  - `VideoPlayerScreen` (Hardware accelerated video player)
  - `ImageViewerScreen`, `PdfViewerScreen`, `TextEditorScreen`
  - `VaultScreen` (AES-256 secure encrypted storage)
  - `FastShareScreen` & `WebShareScreen` (Zero-install wireless web transfer)
  - `DuplicateFinderScreen`, `StorageAnalyzerScreen`, `TimeMachineScreen`
  - `AppManagerScreen`, `AppInstallerScreen`, `ZipViewerScreen`
  - `DualPaneView` (Split-screen multi-tasking)
  - `CtStorageWidgetProvider` (Homescreen widget)

---

## 📋 Quick Checklist for Any AI Session

Upon opening this repository in AI Studio:
- [x] Did you keep `applicationId = "com.pkstudio.ctexplorer.app"`? **YES (Mandatory)**
- [x] Did you keep `namespace = "com.ct.explorer"`? **YES (Mandatory)**
- [x] Did you preserve existing screens and repositories without deleting? **YES (Mandatory)**
- [x] Did you verify clean build using `compile_applet`? **YES (Mandatory)**
