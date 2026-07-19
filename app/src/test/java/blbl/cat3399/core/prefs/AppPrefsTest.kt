package blbl.cat3399.core.prefs

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppPrefsTest {
    @Test
    fun normalizePlayerAutoSkipServerBaseUrl_should_trim_and_remove_trailing_slash() {
        val normalized = AppPrefs.normalizePlayerAutoSkipServerBaseUrl("  https://bsbsb.top/  ")

        assertEquals("https://bsbsb.top", normalized)
    }

    @Test
    fun normalizePlayerAutoSkipServerBaseUrl_should_keep_http_ip_address() {
        val normalized = AppPrefs.normalizePlayerAutoSkipServerBaseUrl("http://154.222.28.109/")

        assertEquals("http://154.222.28.109", normalized)
    }

    @Test
    fun normalizePlayerAutoSkipServerBaseUrl_should_reject_invalid_values() {
        assertNull(AppPrefs.normalizePlayerAutoSkipServerBaseUrl(""))
        assertNull(AppPrefs.normalizePlayerAutoSkipServerBaseUrl("bsbsb.top"))
        assertNull(AppPrefs.normalizePlayerAutoSkipServerBaseUrl("https://bsbsb.top/api?x=1"))
    }

    @Test
    fun normalizeLegacyDanmakuAreaCompat_should_map_legacy_fraction_steps_to_percent_steps() {
        assertEquals(0.2f, AppPrefs.normalizeLegacyDanmakuAreaCompat(1f / 6f), 0.0001f)
        assertEquals(0.3f, AppPrefs.normalizeLegacyDanmakuAreaCompat(0.25f), 0.0001f)
        assertEquals(0.7f, AppPrefs.normalizeLegacyDanmakuAreaCompat(2f / 3f), 0.0001f)
        assertEquals(0.8f, AppPrefs.normalizeLegacyDanmakuAreaCompat(0.75f), 0.0001f)
        assertEquals(1.0f, AppPrefs.normalizeLegacyDanmakuAreaCompat(1.0f), 0.0001f)
    }

    @Test
    fun normalizeDanmakuArea_should_snap_to_nearest_ten_percent_step() {
        assertEquals(0.1f, AppPrefs.normalizeDanmakuArea(0.09f), 0.0001f)
        assertEquals(0.3f, AppPrefs.normalizeDanmakuArea(0.26f), 0.0001f)
        assertEquals(0.6f, AppPrefs.normalizeDanmakuArea(0.55f), 0.0001f)
        assertEquals(1.0f, AppPrefs.normalizeDanmakuArea(Float.NaN), 0.0001f)
    }

    @Test
    fun normalizeStoredGridSpanOverride_distinguishes_missing_legacy_auto_and_explicit_two() {
        assertNull(AppPrefs.normalizeStoredGridSpanOverride(keyExists = false, storedValue = 2))
        assertNull(AppPrefs.normalizeStoredGridSpanOverride(keyExists = true, storedValue = 0))
        assertEquals(2, AppPrefs.normalizeStoredGridSpanOverride(keyExists = true, storedValue = 2))
        assertEquals(6, AppPrefs.normalizeStoredGridSpanOverride(keyExists = true, storedValue = 99))
    }

    @Test
    fun normalizePlayerOsdButtons_should_remove_retired_player_features_from_legacy_preferences() {
        val normalized =
            AppPrefs.normalizePlayerOsdButtons(
                listOf(
                    AppPrefs.PLAYER_OSD_BTN_DANMAKU,
                    AppPrefs.PLAYER_OSD_BTN_COMMENTS,
                    AppPrefs.PLAYER_OSD_BTN_DETAIL,
                    AppPrefs.PLAYER_OSD_BTN_SPONSOR_SUBMIT,
                    AppPrefs.PLAYER_OSD_BTN_NEXT,
                ),
            )

        assertEquals(
            listOf(AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE, AppPrefs.PLAYER_OSD_BTN_NEXT),
            normalized,
        )
    }

    @Test
    fun defaultPlayerOsdButtons_should_include_social_actions_after_up_entry() {
        assertEquals(
            listOf(
                AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE,
                AppPrefs.PLAYER_OSD_BTN_NEXT,
                AppPrefs.PLAYER_OSD_BTN_SUBTITLE,
                AppPrefs.PLAYER_OSD_BTN_UP,
                AppPrefs.PLAYER_OSD_BTN_LIKE,
                AppPrefs.PLAYER_OSD_BTN_COIN,
                AppPrefs.PLAYER_OSD_BTN_FAV,
                AppPrefs.PLAYER_OSD_BTN_LIST_PANEL,
                AppPrefs.PLAYER_OSD_BTN_ADVANCED,
            ),
            AppPrefs.DEFAULT_PLAYER_OSD_BUTTONS,
        )
    }

    @Test
    fun migratePlayerOsdSocialButtons_should_add_missing_actions_once_after_up_entry() {
        val legacy =
            listOf(
                AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE,
                AppPrefs.PLAYER_OSD_BTN_NEXT,
                AppPrefs.PLAYER_OSD_BTN_UP,
                AppPrefs.PLAYER_OSD_BTN_LIST_PANEL,
            )

        val migrated = AppPrefs.migratePlayerOsdSocialButtons(legacy)

        assertEquals(
            listOf(
                AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE,
                AppPrefs.PLAYER_OSD_BTN_NEXT,
                AppPrefs.PLAYER_OSD_BTN_UP,
                AppPrefs.PLAYER_OSD_BTN_LIKE,
                AppPrefs.PLAYER_OSD_BTN_COIN,
                AppPrefs.PLAYER_OSD_BTN_FAV,
                AppPrefs.PLAYER_OSD_BTN_LIST_PANEL,
            ),
            migrated,
        )
        assertEquals(migrated, AppPrefs.migratePlayerOsdSocialButtons(migrated))
    }

    @Test
    fun migratePlayerOsdSocialButtons_should_preserve_existing_social_order_without_duplicates() {
        val custom =
            listOf(
                AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE,
                AppPrefs.PLAYER_OSD_BTN_FAV,
                AppPrefs.PLAYER_OSD_BTN_UP,
                AppPrefs.PLAYER_OSD_BTN_LIKE,
            )

        assertEquals(
            listOf(
                AppPrefs.PLAYER_OSD_BTN_PLAY_PAUSE,
                AppPrefs.PLAYER_OSD_BTN_FAV,
                AppPrefs.PLAYER_OSD_BTN_UP,
                AppPrefs.PLAYER_OSD_BTN_LIKE,
                AppPrefs.PLAYER_OSD_BTN_COIN,
            ),
            AppPrefs.migratePlayerOsdSocialButtons(custom),
        )
    }
}
