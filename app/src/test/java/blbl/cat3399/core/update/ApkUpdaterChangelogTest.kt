package blbl.cat3399.core.update

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Test

class ApkUpdaterChangelogTest {
    @Test
    fun parseChangelog_should_use_first_version_section() {
        val update =
            ApkUpdater.parseChangelog(
                """
                # CHANGELOG

                ## 0.2.0 - 2026-05-01

                ### 修复

                - 修复启动检查。

                ## 0.1.9

                - 旧版本内容。
                """.trimIndent(),
            )

        assertEquals("0.2.0", update.versionName)
        assertTrue(update.changelog.contains("修复启动检查"))
        assertFalse(update.changelog.contains("旧版本内容"))
    }

    @Test
    fun parseChangelog_should_accept_bracketed_v_prefix() {
        val update =
            ApkUpdater.parseChangelog(
                """
                # CHANGELOG

                ## [v0.3.1]

                - 新版本。
                """.trimIndent(),
            )

        assertEquals("0.3.1", update.versionName)
        assertTrue(update.changelog.contains("新版本"))
    }

    @Test
    fun parseGitHubRelease_should_normalize_tag_and_select_apk_asset() {
        val update =
            ApkUpdater.parseGitHubRelease(
                """
                {
                  "tag_name": "v0.2.0",
                  "body": "- 自动最高画质\n- 修复侧边栏",
                  "assets": [
                    {
                      "name": "checksums.txt",
                      "content_type": "text/plain",
                      "browser_download_url": "https://example.invalid/checksums.txt"
                    },
                    {
                      "name": "app-release.apk",
                      "content_type": "application/vnd.android.package-archive",
                      "browser_download_url": "https://github.com/Rito-w/bili/releases/download/v0.2.0/app-release.apk"
                    }
                  ]
                }
                """.trimIndent(),
            )

        assertEquals("0.2.0", update.versionName)
        assertTrue(update.changelog.contains("自动最高画质"))
        assertEquals(
            "https://github.com/Rito-w/bili/releases/download/v0.2.0/app-release.apk",
            update.apkUrl,
        )
    }

    @Test
    fun parseGitHubRelease_should_reject_release_without_apk_asset() {
        assertThrows(IllegalStateException::class.java) {
            ApkUpdater.parseGitHubRelease(
                """{"tag_name":"v0.2.0","body":"notes","assets":[]}""",
            )
        }
    }
}
