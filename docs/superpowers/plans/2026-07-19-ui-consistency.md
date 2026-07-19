# UI Consistency Implementation Plan

> Execute this plan in the isolated `feat/ui-consistency` worktree.

**Goal:** Align shared top tabs and make like/coin/favorite actions visible, understandable, and upgrade-safe.

**Architecture:** Keep view behavior centralized in `UserScaleTabLayout`; move the focus surface from Material's full TabView background onto the managed label TextView. Keep OSD selection in `AppPrefs`, with a pure migration helper covered by JVM tests and a one-time preference marker.

**Tech Stack:** Kotlin, Android Views/XML, Material Components, JUnit 4, Gradle.

---

### Task 1: Freeze preference behavior with failing tests

**Files:**
- Modify: `app/src/test/java/blbl/cat3399/core/prefs/AppPrefsTest.kt`

Add tests asserting that the default OSD collection contains like/coin/favorite in order, migration adds the three keys once without duplicates, and normalized output remains valid.

Run: `./gradlew testDebugUnitTest --tests blbl.cat3399.core.prefs.AppPrefsTest`
Expected: FAIL before production code changes.

### Task 2: Implement OSD defaults and one-time migration

**Files:**
- Modify: `app/src/main/java/blbl/cat3399/core/prefs/AppPrefs.kt`

Add the social keys to `DEFAULT_PLAYER_OSD_BUTTONS`. Add a pure migration helper and a versioned boolean preference marker. On first read of an existing OSD setting, migrate and persist; later reads respect user changes.

Run the focused preference tests until green.

### Task 3: Center the shared top-tab focus surface

**Files:**
- Modify: `app/src/main/java/blbl/cat3399/core/ui/UserScaleTabLayout.kt`
- Modify: `app/src/main/res/values/styles.xml`
- Modify: `app/src/main/res/values/dimens.xml`

Remove the full TabView background from the shared style. Resolve `blblFocusBgRound` onto the managed label, apply symmetric tokenized padding, preserve duplicate parent state, and include label padding in fit calculations.

Run: `./gradlew testDebugUnitTest checkThemeTokens assembleDebug`

### Task 4: Improve social-action labels and accessibility

**Files:**
- Modify: `app/src/main/res/layout/activity_player.xml`
- Modify: `app/src/main/res/layout/activity_player_texture.xml`
- Modify: `app/src/main/res/layout/item_video_detail_header.xml`
- Modify: `app/src/main/res/values/strings.xml`

Replace generic app-name descriptions for modified OSD actions with action-specific strings. Add concise visible labels to detail social actions while retaining the existing view IDs and handlers.

Run: `./gradlew testDebugUnitTest checkThemeTokens assembleDebug`

### Task 5: TV visual QA

Install the debug APK on the existing TV emulator without clearing data. Capture home Tab, detail actions, and player OSD. Verify D-pad focus order, text centering, visible social buttons, both normal and focused states, and no clipping.

### Task 6: Finish and publish

Review the diff, run the full verification set, commit only intentional files, merge the feature branch into `main`, tag the next patch version, push, and verify the GitHub Release contains downloadable build artifacts.
