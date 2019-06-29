package ons.dsi.usabrowser.di

import ons.dsi.usabrowser.BrowserApp
import ons.dsi.usabrowser.adblock.AssetsAdBlocker
import ons.dsi.usabrowser.adblock.NoOpAdBlocker
import ons.dsi.usabrowser.browser.SearchBoxModel
import ons.dsi.usabrowser.browser.activity.BrowserActivity
import ons.dsi.usabrowser.browser.activity.ThemableBrowserActivity
import ons.dsi.usabrowser.browser.fragment.BookmarksFragment
import ons.dsi.usabrowser.browser.fragment.TabsFragment
import ons.dsi.usabrowser.dialog.LightningDialogBuilder
import ons.dsi.usabrowser.download.DownloadHandler
import ons.dsi.usabrowser.download.LightningDownloadListener
import ons.dsi.usabrowser.reading.activity.ReadingActivity
import ons.dsi.usabrowser.search.SuggestionsAdapter
import ons.dsi.usabrowser.settings.activity.SettingsActivity
import ons.dsi.usabrowser.settings.activity.ThemableSettingsActivity
import ons.dsi.usabrowser.settings.fragment.*
import ons.dsi.usabrowser.utils.ProxyUtils
import ons.dsi.usabrowser.view.LightningChromeClient
import ons.dsi.usabrowser.view.LightningView
import ons.dsi.usabrowser.view.LightningWebClient
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (AppBindsModule::class)])
interface AppComponent {

    fun inject(activity: BrowserActivity)

    fun inject(fragment: BookmarksFragment)

    fun inject(fragment: BookmarkSettingsFragment)

    fun inject(builder: LightningDialogBuilder)

    fun inject(fragment: TabsFragment)

    fun inject(lightningView: LightningView)

    fun inject(activity: ThemableBrowserActivity)

    fun inject(advancedSettingsFragment: AdvancedSettingsFragment)

    fun inject(app: BrowserApp)

    fun inject(proxyUtils: ProxyUtils)

    fun inject(activity: ReadingActivity)

    fun inject(webClient: LightningWebClient)

    fun inject(activity: SettingsActivity)

    fun inject(activity: ThemableSettingsActivity)

    fun inject(listener: LightningDownloadListener)

    fun inject(fragment: PrivacySettingsFragment)

    fun inject(fragment: DebugSettingsFragment)

    fun inject(suggestionsAdapter: SuggestionsAdapter)

    fun inject(chromeClient: LightningChromeClient)

    fun inject(downloadHandler: DownloadHandler)

    fun inject(searchBoxModel: SearchBoxModel)

    fun inject(generalSettingsFragment: GeneralSettingsFragment)

    fun inject(displaySettingsFragment: DisplaySettingsFragment)

    fun provideAssetsAdBlocker(): AssetsAdBlocker

    fun provideNoOpAdBlocker(): NoOpAdBlocker

}
