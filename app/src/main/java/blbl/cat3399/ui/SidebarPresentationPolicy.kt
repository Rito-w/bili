package blbl.cat3399.ui

internal enum class SidebarPresentation {
    EXPANDED,
    COLLAPSED,
    HIDDEN,
}

internal enum class SidebarFocusArea {
    SIDEBAR,
    MAIN,
    OUTSIDE,
}

internal object SidebarPresentationPolicy {
    fun forSidebarFocus(): SidebarPresentation = SidebarPresentation.COLLAPSED

    fun forMainFocus(autoHideSidebar: Boolean): SidebarPresentation =
        if (autoHideSidebar) SidebarPresentation.HIDDEN else SidebarPresentation.COLLAPSED

    fun forObservedFocus(
        focusArea: SidebarFocusArea,
        autoHideSidebar: Boolean,
    ): SidebarPresentation? =
        when (focusArea) {
            SidebarFocusArea.SIDEBAR -> forSidebarFocus()
            SidebarFocusArea.MAIN -> forMainFocus(autoHideSidebar)
            SidebarFocusArea.OUTSIDE -> null
        }
}

internal object SidebarLayoutPolicy {
    fun reservedRailWidthPx(
        presentation: SidebarPresentation,
        collapsedWidthPx: Int,
    ): Int =
        when (presentation) {
            SidebarPresentation.EXPANDED,
            SidebarPresentation.COLLAPSED,
            SidebarPresentation.HIDDEN,
            -> collapsedWidthPx.coerceAtLeast(0)
        }
}

internal data class SidebarFocusLabelState(
    val text: String,
    val visible: Boolean,
)

internal object SidebarFocusLabelPolicy {
    fun forFocus(
        label: String,
        hasFocus: Boolean,
    ): SidebarFocusLabelState {
        val normalized = label.trim()
        return if (hasFocus && normalized.isNotEmpty()) {
            SidebarFocusLabelState(text = normalized, visible = true)
        } else {
            SidebarFocusLabelState(text = "", visible = false)
        }
    }
}

internal enum class SidebarEntryAction {
    FOCUS_SELECTED_NAV,
    DEFER_TO_SYSTEM,
}

internal object SidebarEntryPolicy {
    fun actionForDpadLeft(
        focusedStartPx: Int,
        mainContainerStartPx: Int,
        safeContentInsetPx: Int,
        edgeSlopPx: Int,
        sidebarPresentation: SidebarPresentation,
    ): SidebarEntryAction {
        if (sidebarPresentation == SidebarPresentation.EXPANDED) {
            return SidebarEntryAction.DEFER_TO_SYSTEM
        }
        val relativeStartPx = (focusedStartPx - mainContainerStartPx).coerceAtLeast(0)
        val entryThresholdPx = safeContentInsetPx.coerceAtLeast(0) + edgeSlopPx.coerceAtLeast(0)
        return if (relativeStartPx <= entryThresholdPx) {
            SidebarEntryAction.FOCUS_SELECTED_NAV
        } else {
            SidebarEntryAction.DEFER_TO_SYSTEM
        }
    }
}

