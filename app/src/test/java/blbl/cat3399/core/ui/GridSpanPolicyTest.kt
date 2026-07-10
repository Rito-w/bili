package blbl.cat3399.core.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class GridSpanPolicyTest {
    @Test
    fun video_default_is_two_columns_at_tv_baseline() {
        assertEquals(
            2,
            GridSpanPolicy.videoSpanCountForWidthDp(
                widthDp = 960f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
    }

    @Test
    fun video_default_stays_two_columns_at_supported_narrow_width() {
        // 600dp is the smallest supported two-card browse width; below it we step down to one.
        assertEquals(
            2,
            GridSpanPolicy.videoSpanCountForWidthDp(
                widthDp = 600f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
        assertEquals(
            1,
            GridSpanPolicy.videoSpanCountForWidthDp(
                widthDp = 480f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
    }

    @Test
    fun pgc_default_is_four_columns_at_tv_baseline_and_three_when_narrower() {
        assertEquals(
            4,
            GridSpanPolicy.pgcSpanCountForWidthDp(
                widthDp = 960f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
        assertEquals(
            3,
            GridSpanPolicy.pgcSpanCountForWidthDp(
                widthDp = 600f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
    }

    @Test
    fun live_default_is_three_columns_at_tv_baseline_and_two_when_narrower() {
        assertEquals(
            3,
            GridSpanPolicy.liveSpanCountForWidthDp(
                widthDp = 960f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
        assertEquals(
            2,
            GridSpanPolicy.liveSpanCountForWidthDp(
                widthDp = 600f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
    }

    @Test
    fun live_default_keeps_three_columns_for_real_tv_browse_content_width() {
        // The 960dp viewport yields about 780dp after the collapsed rail, safe area and grid padding.
        assertEquals(
            3,
            GridSpanPolicy.liveSpanCountForWidthDp(
                widthDp = 780f,
                overrideSpanCount = null,
                uiScale = 1f,
            ),
        )
    }

    @Test
    fun explicit_user_overrides_win_for_every_content_kind_and_are_clamped() {
        for (span in 1..6) {
            assertEquals(span, GridSpanPolicy.videoSpanCountForWidthDp(960f, span, 1f))
            assertEquals(span, GridSpanPolicy.pgcSpanCountForWidthDp(960f, span, 1f))
            assertEquals(span, GridSpanPolicy.liveSpanCountForWidthDp(960f, span, 1f))
        }

        assertEquals(1, GridSpanPolicy.videoSpanCountForWidthDp(960f, -10, 1f))
        assertEquals(6, GridSpanPolicy.pgcSpanCountForWidthDp(960f, 99, 1f))
        assertEquals(6, GridSpanPolicy.liveSpanCountForWidthDp(960f, 7, 1f))
    }

    @Test
    fun ui_scale_reduces_effective_width_before_selecting_automatic_span() {
        assertEquals(
            3,
            GridSpanPolicy.pgcSpanCountForWidthDp(
                widthDp = 960f,
                overrideSpanCount = null,
                uiScale = 1.4f,
            ),
        )
        assertEquals(
            2,
            GridSpanPolicy.liveSpanCountForWidthDp(
                widthDp = 960f,
                overrideSpanCount = null,
                uiScale = 1.4f,
            ),
        )
    }
}
