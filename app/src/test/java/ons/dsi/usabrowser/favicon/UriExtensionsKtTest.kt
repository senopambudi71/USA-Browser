package ons.dsi.usabrowser.favicon

import ons.dsi.usabrowser.BuildConfig
import ons.dsi.usabrowser.SDK_VERSION
import ons.dsi.usabrowser.TestApplication
import androidx.core.net.toUri
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Unit tests for UriExtensions.kt
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class, sdk = [SDK_VERSION])
class UriExtensionsKtTest {

    @Test
    fun `safeUri returns null for empty url`() = assertThat("".toUri().toValidUri()).isNull()

    @Test
    fun `safeUri returns null for url without scheme`() = assertThat("test.com".toUri().toValidUri()).isNull()

    @Test
    fun `safeUri returns null for url without host`() = assertThat("http://".toUri().toValidUri()).isNull()

    @Test
    fun `safeUri returns valid Uri for full url`() {
        assertThat("http://test.com".toUri().toValidUri()).isEqualTo(ValidUri("http", "test.com"))
    }
}
