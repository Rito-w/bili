package blbl.cat3399.feature.player.engine

import org.junit.Assert.assertEquals
import org.junit.Test

class IjkPluginReleaseUrlsTest {
    @Test
    fun ijkPluginReleaseUrl_should_use_this_projects_latest_github_release() {
        assertEquals(
            "https://github.com/Rito-w/bili/releases/latest/download/libijkplayer-arm64-v8a.zip",
            ijkPluginReleaseUrl("arm64-v8a"),
        )
    }
}
