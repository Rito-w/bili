package blbl.cat3399.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class SidebarPresentationPolicyTest {
    @Test
    fun sidebarFocus_expandsAndShowsTheNavigationLabels() {
        assertEquals(SidebarPresentation.EXPANDED, SidebarPresentationPolicy.forSidebarFocus())
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
}
