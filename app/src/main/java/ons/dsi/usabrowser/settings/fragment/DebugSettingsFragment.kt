package ons.dsi.usabrowser.settings.fragment

import ons.dsi.usabrowser.R
import ons.dsi.usabrowser.di.injector
import ons.dsi.usabrowser.extensions.snackbar
import ons.dsi.usabrowser.preference.DeveloperPreferences
import android.os.Bundle
import javax.inject.Inject

class DebugSettingsFragment : AbstractSettingsFragment() {

    @Inject internal lateinit var developerPreferences: DeveloperPreferences

    override fun providePreferencesXmlResource() = R.xml.preference_debug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        togglePreference(
            preference = LEAK_CANARY,
            isChecked = developerPreferences.useLeakCanary,
            onCheckChange = { change ->
                activity?.apply { snackbar(R.string.app_restart) }
                developerPreferences.useLeakCanary = change
            }
        )
    }

    companion object {
        private const val LEAK_CANARY = "leak_canary_enabled"
    }
}
