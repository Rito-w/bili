package blbl.cat3399.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class SidebarPresentationPolicyTest {
    @Test
    fun sidebarFocus_keepsTheNavigationAsAnIconRail() {
        assertEquals(SidebarPresentation.COLLAPSED, SidebarPresentationPolicy.forSidebarFocus())
    }

    @Test
    fun mainFocus_keepsAnIconRailWhenAutoHideIsDisabled() {
        assertEquals(
            SidebarPresentation.COLLAPSED,
            SidebarPresentationPolicy.forMainFocus(autoHideSidebar = false),
        )
    }

    @Test
    fun mainFocus_hidesTheSidebarWhenAutoHideIsEnabled() {
        assertEquals(
            SidebarPresentation.HIDDEN,
            SidebarPresentationPolicy.forMainFocus(autoHideSidebar = true),
        )
    }

    @Test
    fun observedMainFocus_collapsesWithoutRequiringAnArmedDpadTransfer() {
        assertEquals(
            SidebarPresentation.COLLAPSED,
            SidebarPresentationPolicy.forObservedFocus(
                focusArea = SidebarFocusArea.MAIN,
                autoHideSidebar = false,
            ),
        )
    }

    @Test
    fun everyPresentation_reservesTheSameRailWidthSoMainContentNeverJumps() {
        val collapsedWidthPx = 96

        assertEquals(collapsedWidthPx, SidebarLayoutPolicy.reservedRailWidthPx(SidebarPresentation.EXPANDED, collapsedWidthPx))
        assertEquals(collapsedWidthPx, SidebarLayoutPolicy.reservedRailWidthPx(SidebarPresentation.COLLAPSED, collapsedWidthPx))
        assertEquals(collapsedWidthPx, SidebarLayoutPolicy.reservedRailWidthPx(SidebarPresentation.HIDDEN, collapsedWidthPx))
    }

    @Test
    fun focusedNavigation_showsASeparateLabelWithoutChangingRailPresentation() {
        assertEquals(
            SidebarFocusLabelState(text = "首页", visible = true),
            SidebarFocusLabelPolicy.forFocus(label = "首页", hasFocus = true),
        )
        assertEquals(
            SidebarFocusLabelState(text = "", visible = false),
            SidebarFocusLabelPolicy.forFocus(label = "首页", hasFocus = false),
        )
        assertEquals(
            SidebarPresentation.COLLAPSED,
            SidebarPresentationPolicy.forSidebarFocus(),
        )
    }

    @Test
    fun blankNavigationLabel_neverShowsAnEmptyFloatingPanel() {
        assertEquals(
            SidebarFocusLabelState(text = "", visible = false),
            SidebarFocusLabelPolicy.forFocus(label = "  ", hasFocus = true),
        )
    }
}
