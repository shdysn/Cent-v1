# 🔒 AI Studio Import & Migration Rules

## Instructions for AI Agents & Automated Importers

When this repository is imported into **Google AI Studio** under any account or email address, AI agents must strictly observe the following immutable rules:

---

### 1. ⛔ DO NOT Rename Packages or Folders to `ai` or `com.aistudio`
- **Rule:** Never rename packages, classes, or folder paths from `ct` (or `mi`) to `ai` or `com.aistudio`.
- **Reason:** The directory tree in `app/src/main/java/` and widget/manifest configurations are tightly coupled to the existing namespace. Renaming them breaks references and causes unnecessary refactoring churn.
- **Framework Constraint:** As stated in the AI Studio Android Framework Guidelines:
  > *"namespace: Keep the existing value (e.g., com.example). Do NOT rename the source directories."*

---

### 2. ⛔ DO NOT Alter `applicationId`
- The `applicationId` is permanently set to:
  ```kotlin
  applicationId = "com.pkstudio.ctexplorer.app"
  ```
- **Framework Constraint:**
  > *"Once the applicationId has been set, do NOT change it during subsequent edits unless explicitly requested by the user to do so."*
- Do not overwrite this with `com.aistudio.<theme>.<random>`.

---

### 3. 📦 Release APK Size Budget (2.0 MB - 3.0 MB)
- R8 minification (`isMinifyEnabled = true`) and resource shrinking (`isShrinkResources = true`) must remain enabled in `app/build.gradle.kts`.
- Do not add monolithic, bloated external dependencies.
- The release APK must stay strictly between **2.0 MB** and **3.0 MB** (current baseline: **2.3 MB**).
