# BLBL TV UI Redesign Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Rebuild the complete BLBL interface as a cohesive, readable, D-pad-first Android TV experience and publish verified emulator screenshots in the README.

**Architecture:** Keep the existing Kotlin/XML/ViewBinding architecture and data flows. Centralize TV design tokens in Android resources, apply them to shared components first, then migrate page groups while preserving focus and paging behavior. Kotlin behavior changes are test-first; XML-only visual changes are verified by resource compilation, lint, and emulator screenshots.

**Tech Stack:** Kotlin, Android XML, Material Components, RecyclerView, ViewPager2, ViewBinding, Gradle, Android TV emulator.

## Global Constraints

- Keep existing API, account, paging, playback-engine, quality-selection, and long-press action semantics unchanged.
- Keep user-selectable theme, UI scale, grid span, and player preferences working.
- Use a 960×540 mdpi TV design baseline with at least 48dp horizontal and 24dp vertical safe margins for important content.
- Use `#05070A` page background, `#121720` primary surface, `#1B2230` secondary surface, `#F7F9FC` primary text, `#B5BFCE` secondary text, `#8995A7` weak text, `#FB7299` brand accent, and `#FF4D5E` live/danger semantics.
- Use 14dp card corners, 20dp panel corners, and the full focused-card combination of 3dp ring, focused surface lift, and 12dp visual elevation.
- Default video grid is two columns; default 12:17 PGC/poster grid is four columns.
- Card focus uses 1.05 scale, 3dp outline, surface lift, and 180ms motion; small action focus may use 1.08 scale.
- Main card title is 22sp and supporting content is at least 16sp; page title is 30sp and top tabs are 24sp.
- Preserve the current custom D-pad-down next-video behavior and add a visible hint instead of remapping the key.
- Do not migrate the project to Compose or add a new UI framework.

---

### Task 1: Baseline and TV Design Foundation

**Files:**
- Modify: `app/src/main/res/values/colors.xml`
- Modify: `app/src/main/res/values/dimens.xml`
- Modify: `app/src/main/res/values/styles.xml`
- Modify: `app/src/main/res/values/themes.xml`
- Modify: `app/src/main/res/animator/blbl_focus_scale.xml`
- Create: `app/src/main/res/animator/blbl_focus_scale_button.xml`
- Modify/Create: shared focus, surface, scrim and panel drawables under `app/src/main/res/drawable/`

**Interfaces:**
- Consumes: existing theme attributes in `attrs.xml` and user-scale inflation.
- Produces: centralized color, typography, spacing, shape, safe-area and focus resources consumed by every later task.

- [ ] Record baseline output from `./gradlew :app:testDebugUnitTest` and `./gradlew :app:assembleDebug`.
- [ ] Replace conflicting accent aliases with one default brand accent and separate semantic live/danger colors.
- [ ] Add exact safe-area, typography, card, panel, and focus dimensions from Global Constraints.
- [ ] Implement card and small-button focus animators without width/height animation.
- [ ] Update shared Material styles and theme overlays to consume the centralized tokens.
- [ ] Run `./gradlew :app:processDebugResources :app:lintDebug`; expect exit code 0.

### Task 2: Global Shell, Sidebar and Tabs

**Files:**
- Modify: `app/src/main/res/layout/activity_main.xml`
- Modify: `app/src/main/res/layout/item_sidebar_nav.xml`
- Modify: `app/src/main/res/layout/fragment_home.xml`
- Modify: `app/src/main/res/layout/fragment_category.xml`
- Modify: `app/src/main/res/layout/fragment_live.xml`
- Modify: `app/src/main/res/layout/fragment_my_tabs.xml`
- Modify: `app/src/main/java/blbl/cat3399/ui/MainActivity.kt` only if layout-state synchronization requires it
- Modify: `app/src/main/java/blbl/cat3399/ui/SidebarNavAdapter.kt` only if selected/focused visuals require state binding

**Interfaces:**
- Consumes: Task 1 tokens and existing `SidebarFocusHost`/tab focus helpers.
- Produces: shared safe-area shell and visually distinct selected/focused navigation states.

- [ ] Add safe-area padding and consistent header heights without changing fragment navigation IDs.
- [ ] Redesign collapsed and expanded sidebar states while preserving current click and focus callbacks.
- [ ] Apply one tab style and clock alignment across top-level pages.
- [ ] Verify every visible sidebar destination remains reachable with D-pad.
- [ ] Run `./gradlew :app:assembleDebug`; expect exit code 0.

### Task 3: Shared Cards, Grids and Main Browse Pages

**Files:**
- Modify: `app/src/main/res/layout/item_video_card.xml`
- Modify: `app/src/main/res/layout/item_live_card.xml`
- Modify: `app/src/main/res/layout/item_live_area.xml`
- Modify: `app/src/main/res/layout/item_bangumi_follow.xml`
- Modify: `app/src/main/res/layout/item_following_grid.xml`
- Modify: `app/src/main/res/layout/fragment_video_grid.xml`
- Modify: `app/src/main/res/layout/fragment_live_grid.xml`
- Modify: `app/src/main/res/layout/fragment_dynamic.xml`
- Modify: `app/src/main/java/blbl/cat3399/core/ui/GridSpanPolicy.kt`
- Modify: `app/src/main/java/blbl/cat3399/core/prefs/AppPrefs.kt`
- Test: `app/src/test/java/blbl/cat3399/core/ui/GridSpanPolicyTest.kt`

**Interfaces:**
- Consumes: existing `VideoCardAdapter`, `BangumiFollowAdapter`, live adapters and Task 1 resources.
- Produces: stable-size TV cards, two-column default video grids and four-column default poster grids.

- [ ] Write tests asserting automatic/default video and poster span behavior at 960dp and narrower widths.
- [ ] Run the focused test and confirm it fails for the old poster default or policy.
- [ ] Implement minimal span/default changes and run the focused test to green.
- [ ] Rebuild video, live and poster cards with fixed content blocks, larger text and unified media scrims.
- [ ] Reserve clipping/padding budget for 1.05 focused-card scaling.
- [ ] Run `./gradlew :app:testDebugUnitTest :app:assembleDebug`; expect exit code 0.

### Task 4: Search, Dynamic, My and Secondary Content Flows

**Files:**
- Modify: `app/src/main/res/layout/fragment_search.xml`
- Modify: `app/src/main/res/layout/item_search_key.xml`
- Modify: `app/src/main/res/layout/item_search_hot.xml`
- Modify: `app/src/main/res/layout/item_search_suggest.xml`
- Modify: `app/src/main/res/layout/fragment_dynamic.xml`
- Modify: `app/src/main/res/layout/item_following.xml`
- Modify: `app/src/main/res/layout/fragment_my_container.xml`
- Modify: `app/src/main/res/layout/fragment_my_login.xml`
- Modify: relevant favorites/history/following layouts under `app/src/main/res/layout/`
- Modify: search/dynamic renderer Kotlin only where focus geometry cannot be expressed in XML

**Interfaces:**
- Consumes: shared shell, cards and focus resources.
- Produces: coherent D-pad paths for search keyboard/results, dynamic two-pane layout, and personal content pages.

- [ ] Increase search key targets and reorganize search/history/hot areas without changing query behavior.
- [ ] Make dynamic left pane readable and give each focus direction a predictable destination.
- [ ] Apply common cards, empty/loading states and typography to My and secondary lists.
- [ ] Run `./gradlew :app:lintDebug :app:assembleDebug`; expect exit code 0.

### Task 5: Video Detail and Player OSD

**Files:**
- Modify: `app/src/main/res/layout/activity_video_detail.xml`
- Modify: `app/src/main/res/layout/item_video_detail_header.xml`
- Modify: `app/src/main/res/layout/item_video_detail_playlist.xml`
- Modify: `app/src/main/res/layout/activity_player.xml`
- Modify: `app/src/main/res/layout/include_player_side_panels.xml`
- Modify: `app/src/main/res/layout/item_player_setting.xml`
- Modify: `app/src/main/res/layout/item_player_comment.xml`
- Modify: `app/src/main/res/layout/item_player_info_recommend.xml`
- Modify: `app/src/main/res/values/dimens_player_osd.xml`
- Modify: player UI Kotlin only to surface the existing D-pad-down hint or bind visual states

**Interfaces:**
- Consumes: current PlayerActivity behavior, fixed player theme and shared TV tokens.
- Produces: readable two-pane details, enlarged player controls and consistent side panels without playback behavior changes.

- [ ] Recompose detail header hierarchy with prominent title, UP info, description and primary play action.
- [ ] Enlarge OSD controls, time/progress, settings rows, comments and recommendation surfaces.
- [ ] Add a visible hint for the existing next-video key mapping without changing dispatch logic.
- [ ] Verify all existing player buttons remain in the configured OSD list and focus order.
- [ ] Run player-related unit tests and `./gradlew :app:assembleDebug`; expect exit code 0.

### Task 6: Settings, Login, Dialogs and Feedback

**Files:**
- Modify: `app/src/main/res/layout/activity_settings.xml`
- Modify: `app/src/main/res/layout/item_settings_left.xml`
- Modify: `app/src/main/res/layout/item_setting_entry.xml`
- Modify: `app/src/main/res/layout/activity_qr_login.xml`
- Modify: `app/src/main/res/layout/dialog_user_info.xml`
- Modify: popup/dialog/toast layouts under `app/src/main/res/layout/`
- Modify: matching shared drawables and dimensions only through Task 1 token names

**Interfaces:**
- Consumes: current settings renderer/interaction handler and popup host behavior.
- Produces: 64dp-or-larger settings rows, TV-readable QR flow, and consistent modal/action states.

- [ ] Apply the two-pane settings hierarchy and consistent focus-safe row padding.
- [ ] Increase QR, account and helper text presentation for 10-foot viewing.
- [ ] Standardize modal, choice, destructive, loading, empty, error and toast surfaces.
- [ ] Run `./gradlew :app:lintDebug :app:testDebugUnitTest :app:assembleDebug`; expect exit code 0.

### Task 7: Emulator Visual QA, Screenshots and README

**Files:**
- Replace/Add: PNG screenshots under `docs/screenshots/`
- Modify: `README.md`
- Modify: UI files found defective during visual QA

**Interfaces:**
- Consumes: debug APK and configured Android TV AVD.
- Produces: authoritative 1920×1080 runtime screenshots and updated project documentation.

- [ ] Start or create a 1920×1080 Android TV AVD, install with `adb install -r app/build/outputs/apk/debug/app-debug.apk`, and launch the main activity.
- [ ] Capture home, hot, bangumi, movie, search, live, dynamic, my, detail and player states after content finishes loading.
- [ ] Inspect each screenshot for safe margins, focus visibility, text clipping, density, loading artifacts and image distortion; fix defects and recapture.
- [ ] Exercise D-pad navigation and Back on every major flow, recording any unreachable control as a defect before completion.
- [ ] Functionally verify cold start, disclaimer, sidebar destination switching, tab switching, refresh, search entry/results, video-detail opening, playback start/pause/seek/OSD, next-video shortcut, settings changes, QR-login screen, Back behavior and double-Back exit; capture logcat around crashes or failed transitions.
- [ ] Verify theme switching, UI-scale changes, video/PGC grid-count changes and activity recreation still preserve readable layout and focus.
- [ ] Update README feature bullets and screenshot gallery with the final emulator captures.
- [ ] Run fresh final verification: `./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug`; expect all tasks successful and exit code 0.
