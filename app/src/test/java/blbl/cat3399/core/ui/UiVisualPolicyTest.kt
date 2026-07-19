package blbl.cat3399.core.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class UiVisualPolicyTest {
    @Test
    fun cinematicPinkTokens_keepAConsistentGeometryAndMotionRhythm() {
        assertEquals(8, UiVisualPolicy.BASE_SPACING_DP)
        assertEquals(16, UiVisualPolicy.CARD_CORNER_RADIUS_DP)
        assertEquals(20, UiVisualPolicy.PANEL_CORNER_RADIUS_DP)
        assertEquals(3, UiVisualPolicy.FOCUS_OUTLINE_WIDTH_DP)
        assertEquals(160L, UiVisualPolicy.FOCUS_MOTION_DURATION_MS)
        assertEquals(220L, UiVisualPolicy.PANEL_MOTION_DURATION_MS)
    }

    @Test
    fun focusScale_isRestrainedByComponentRole() {
        assertEquals(
            FocusVisualSpec(scale = 1.04f, durationMs = 160L),
            TvFocusVisualPolicy.forKind(FocusVisualKind.CARD),
        )
        assertEquals(
            FocusVisualSpec(scale = 1.02f, durationMs = 160L),
            TvFocusVisualPolicy.forKind(FocusVisualKind.BUTTON),
        )
        assertEquals(
            FocusVisualSpec(scale = 1.0f, durationMs = 220L),
            TvFocusVisualPolicy.forKind(FocusVisualKind.PANEL),
        )
    }
}
