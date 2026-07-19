package blbl.cat3399.feature.settings

import blbl.cat3399.core.prefs.AppPrefs
import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsTextTest {
    @Test
    fun `api source uses a localized label`() {
        assertEquals("App 接口", SettingsText.apiSourceText(AppPrefs.API_SOURCE_APP))
        assertEquals("Web 接口", SettingsText.apiSourceText(AppPrefs.API_SOURCE_WEB))
    }

    @Test
    fun `render view explains the technical implementation`() {
        assertEquals(
            "纹理视图（TextureView）",
            SettingsText.renderViewText(AppPrefs.PLAYER_RENDER_VIEW_TEXTURE_VIEW),
        )
        assertEquals(
            "表面视图（SurfaceView）",
            SettingsText.renderViewText(AppPrefs.PLAYER_RENDER_VIEW_SURFACE_VIEW),
        )
    }

    @Test
    fun `player engine is presented as a player choice`() {
        assertEquals("IJK 播放器", SettingsText.playerEngineText(AppPrefs.PLAYER_ENGINE_IJK))
        assertEquals("ExoPlayer 播放器", SettingsText.playerEngineText(AppPrefs.PLAYER_ENGINE_EXO))
    }

    @Test
    fun `image quality values are localized`() {
        assertEquals("省流", SettingsText.imageQualityText("small"))
        assertEquals("标准", SettingsText.imageQualityText("medium"))
        assertEquals("高清", SettingsText.imageQualityText("large"))
    }
}
