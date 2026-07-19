package blbl.cat3399.core.ui

object UiVisualPolicy {
    const val BASE_SPACING_DP = 8
    const val CARD_CORNER_RADIUS_DP = 16
    const val PANEL_CORNER_RADIUS_DP = 20
    const val FOCUS_OUTLINE_WIDTH_DP = 3
    const val FOCUS_MOTION_DURATION_MS = 160L
    const val PANEL_MOTION_DURATION_MS = 220L
}

enum class FocusVisualKind {
    CARD,
    BUTTON,
    PANEL,
}

data class FocusVisualSpec(
    val scale: Float,
    val durationMs: Long,
)

object TvFocusVisualPolicy {
    fun forKind(kind: FocusVisualKind): FocusVisualSpec =
        when (kind) {
            FocusVisualKind.CARD ->
                FocusVisualSpec(
                    scale = 1.04f,
                    durationMs = UiVisualPolicy.FOCUS_MOTION_DURATION_MS,
                )

            FocusVisualKind.BUTTON ->
                FocusVisualSpec(
                    scale = 1.02f,
                    durationMs = UiVisualPolicy.FOCUS_MOTION_DURATION_MS,
                )

            FocusVisualKind.PANEL ->
                FocusVisualSpec(
                    scale = 1.0f,
                    durationMs = UiVisualPolicy.PANEL_MOTION_DURATION_MS,
                )
        }
}

