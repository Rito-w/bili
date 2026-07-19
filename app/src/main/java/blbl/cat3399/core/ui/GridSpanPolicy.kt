package blbl.cat3399.core.ui

object GridSpanPolicy {
    private const val MIN_SPAN = 1
    private const val MAX_SPAN = 6

    /**
     * Resolves the large 16:9 browse-card grid.
     *
     * A nullable override is intentional: `null` means that the user has never selected a
     * column count (or kept the legacy automatic value), while a stored value such as `2`
     * must win over content-specific defaults.
     */
    fun videoSpanCountForWidthDp(
        widthDp: Float,
        overrideSpanCount: Int?,
        uiScale: Float,
    ): Int =
        resolveSpanCount(overrideSpanCount) {
            when {
                effectiveWidthDp(widthDp, uiScale) >= 600f -> 2
                else -> 1
            }
        }

    /** Resolves the anime poster grid: two wide cards on the TV baseline. */
    fun animeSpanCountForWidthDp(
        widthDp: Float,
        overrideSpanCount: Int?,
        uiScale: Float,
    ): Int =
        resolveSpanCount(overrideSpanCount) {
            when {
                effectiveWidthDp(widthDp, uiScale) >= 600f -> 2
                else -> 1
            }
        }

    /** Resolves the cinematic PGC grid: two readable landscape cards on the TV baseline. */
    fun pgcSpanCountForWidthDp(
        widthDp: Float,
        overrideSpanCount: Int?,
        uiScale: Float,
    ): Int =
        resolveSpanCount(overrideSpanCount) {
            when {
                effectiveWidthDp(widthDp, uiScale) >= 600f -> 2
                else -> 1
            }
        }

    /** Resolves the live-room grid: 3 columns at the 960dp TV baseline. */
    fun liveSpanCountForWidthDp(
        widthDp: Float,
        overrideSpanCount: Int?,
        uiScale: Float,
    ): Int =
        resolveSpanCount(overrideSpanCount) {
            when {
                effectiveWidthDp(widthDp, uiScale) >= 780f -> 3
                effectiveWidthDp(widthDp, uiScale) >= 560f -> 2
                else -> 1
            }
        }

    fun fixedSpanCountForWidthDp(widthDp: Float, overrideSpanCount: Int): Int {
        return videoSpanCountForWidthDp(
            widthDp = widthDp,
            overrideSpanCount = overrideSpanCount.takeIf { it > 0 },
            uiScale = 1f,
        )
    }

    fun dynamicSpanCountForWidthDp(widthDp: Float, dynamicOverrideSpanCount: Int, globalOverrideSpanCount: Int): Int {
        if (dynamicOverrideSpanCount > 0) return dynamicOverrideSpanCount.coerceIn(MIN_SPAN, MAX_SPAN)
        return fixedSpanCountForWidthDp(widthDp = widthDp, overrideSpanCount = globalOverrideSpanCount)
    }

    fun autoSpanCountForWidthDp(
        widthDp: Float,
        overrideSpanCount: Int,
        uiScale: Float,
        minCardWidthDp: Float = 210f,
        minSpan: Int = 2,
        maxSpan: Int = MAX_SPAN,
    ): Int {
        if (overrideSpanCount > 0) return overrideSpanCount.coerceIn(MIN_SPAN, MAX_SPAN)
        val minWidthDp = minCardWidthDp * uiScale
        val raw = (widthDp / minWidthDp).toInt()
        return raw.coerceIn(minSpan, maxSpan)
    }

    private inline fun resolveSpanCount(overrideSpanCount: Int?, automatic: () -> Int): Int {
        return overrideSpanCount?.coerceIn(MIN_SPAN, MAX_SPAN) ?: automatic().coerceIn(MIN_SPAN, MAX_SPAN)
    }

    private fun effectiveWidthDp(widthDp: Float, uiScale: Float): Float {
        val safeWidth = widthDp.takeIf { it.isFinite() && it > 0f } ?: 0f
        val safeScale = uiScale.takeIf { it.isFinite() && it > 0f } ?: 1f
        return safeWidth / safeScale
    }
}
