package blbl.cat3399.ui

import android.content.Context
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvFocusNavigationTest {
    private lateinit var device: UiDevice
    private lateinit var context: Context

    @Before
    fun launchApp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        context = instrumentation.targetContext
        val launchIntent =
            requireNotNull(context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)) {
                "Missing launcher intent for $PACKAGE_NAME"
            }.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        context.startActivity(launchIntent)
        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "sidebar")), TIMEOUT_MS))
        device.findObject(By.text("接受并继续"))?.click()
        if (device.wait(Until.hasObject(By.textStartsWith("发现新版本")), UPDATE_PROMPT_TIMEOUT_MS)) {
            device.pressBack()
        }
        device.waitForIdle()
    }

    @Test
    fun homeSidebarMovesFocusWithoutChangingWidth() {
        val sidebar = requireNotNull(device.findObject(By.res(PACKAGE_NAME, "sidebar")))
        val widthBefore = sidebar.visibleBounds.width()
        val focusBefore = device.findObject(By.focused(true))?.visibleBounds

        device.pressDPadDown()
        device.waitForIdle()

        val focusAfter = device.findObject(By.focused(true))
        assertNotNull("DPAD_DOWN must leave a visible focused target", focusAfter)
        assertNotEquals("Focus must move to another TV target", focusBefore, focusAfter?.visibleBounds)
        assertTrue(widthBefore == sidebar.visibleBounds.width())
    }

    @Test
    fun settingsKeepsLeftAndRightColumnsReachable() {
        val settings = requireNotNull(device.findObject(By.res(PACKAGE_NAME, "btn_sidebar_settings")))
        settings.click()
        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "recycler_left")), TIMEOUT_MS))
        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "recycler_right")), TIMEOUT_MS))

        requireNotNull(device.findObject(By.text("通用设置"))).click()
        device.pressDPadRight()
        device.waitForIdle()

        assertNotNull("Settings content must receive focus from the category column", device.findObject(By.focused(true)))
    }

    @Test
    fun searchKeyboardAndHotListRemainReachable() {
        val search =
            device.findObjects(By.desc("搜索"))
                .firstOrNull { it.isClickable }
        requireNotNull(search) { "Search sidebar entry is missing" }.click()

        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "btn_clear")), TIMEOUT_MS))
        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "recycler_keys")), TIMEOUT_MS))
        assertTrue(device.wait(Until.hasObject(By.res(PACKAGE_NAME, "recycler_hot")), TIMEOUT_MS))
    }

    private companion object {
        const val PACKAGE_NAME = "blbl.cat3399"
        const val TIMEOUT_MS = 8_000L
        const val UPDATE_PROMPT_TIMEOUT_MS = 4_000L
    }
}
