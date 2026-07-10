package blbl.cat3399.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class SidebarEntryPolicyTest {
    @Test
    fun safeAreaAndCardMargin_focusSelectedNavFromCollapsedRail() {
        assertEquals(
            SidebarEntryAction.FOCUS_SELECTED_NAV,
            actionForFirstColumn(sidebarPresentation = SidebarPresentation.COLLAPSED),
        )
    }

    @Test
    fun safeAreaAndCardMargin_revealHiddenSidebarAndFocusSelectedNav() {
        assertEquals(
            SidebarEntryAction.FOCUS_SELECTED_NAV,
            actionForFirstColumn(sidebarPresentation = SidebarPresentation.HIDDEN),
        )
    }

    @Test
    fun itemBeyondSafeAreaAndEdgeSlop_defersToSystemFocusSearch() {
        assertEquals(
            SidebarEntryAction.DEFER_TO_SYSTEM,
            SidebarEntryPolicy.actionForDpadLeft(
                focusedStartPx = 73,
                mainContainerStartPx = 0,
                safeContentInsetPx = 48,
                edgeSlopPx = 24,
                sidebarPresentation = SidebarPresentation.COLLAPSED,
            ),
        )
    }

    private fun actionForFirstColumn(sidebarPresentation: SidebarPresentation): SidebarEntryAction {
        val mainContainerStartPx = if (sidebarPresentation == SidebarPresentation.COLLAPSED) 96 else 0
        return SidebarEntryPolicy.actionForDpadLeft(
            focusedStartPx = mainContainerStartPx + 60,
            mainContainerStartPx = mainContainerStartPx,
            safeContentInsetPx = 48,
            edgeSlopPx = 24,
            sidebarPresentation = sidebarPresentation,
        )
    }
}
