package blbl.cat3399.feature.player

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PlayerRenderGeometryTest {
    @Test
    fun keepsLandscapeDimensionsWhenVideoIsNotRotated() {
        assertEquals(
            PlayerRenderGeometry(width = 1920, height = 1080),
            resolvePlayerRenderGeometry(width = 1920, height = 1080, rotate = 0),
        )
    }

    @Test
    fun swapsDimensionsWhenVideoMetadataRequiresRotation() {
        assertEquals(
            PlayerRenderGeometry(width = 1080, height = 1920),
            resolvePlayerRenderGeometry(width = 1920, height = 1080, rotate = 1),
        )
    }

    @Test
    fun rejectsInvalidDimensions() {
        assertNull(resolvePlayerRenderGeometry(width = 0, height = 1080, rotate = 0))
        assertNull(resolvePlayerRenderGeometry(width = 1920, height = -1, rotate = 0))
    }
}
