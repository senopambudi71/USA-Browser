package ons.dsi.usabrowser.di

import ons.dsi.usabrowser.adblock.allowlist.AllowListModel
import ons.dsi.usabrowser.adblock.allowlist.SessionAllowListModel
import ons.dsi.usabrowser.database.allowlist.AdBlockAllowListDatabase
import ons.dsi.usabrowser.database.allowlist.AdBlockAllowListRepository
import ons.dsi.usabrowser.database.bookmark.BookmarkDatabase
import ons.dsi.usabrowser.database.bookmark.BookmarkRepository
import ons.dsi.usabrowser.database.downloads.DownloadsDatabase
import ons.dsi.usabrowser.database.downloads.DownloadsRepository
import ons.dsi.usabrowser.database.history.HistoryDatabase
import ons.dsi.usabrowser.database.history.HistoryRepository
import ons.dsi.usabrowser.ssl.SessionSslWarningPreferences
import ons.dsi.usabrowser.ssl.SslWarningPreferences
import dagger.Binds
import dagger.Module

/**
 * Dependency injection module used to bind implementations to interfaces.
 */
@Module
abstract class AppBindsModule {

    @Binds
    abstract fun provideBookmarkModel(bookmarkDatabase: BookmarkDatabase): BookmarkRepository

    @Binds
    abstract fun provideDownloadsModel(downloadsDatabase: DownloadsDatabase): DownloadsRepository

    @Binds
    abstract fun providesHistoryModel(historyDatabase: HistoryDatabase): HistoryRepository

    @Binds
    abstract fun providesAdBlockAllowListModel(adBlockAllowListDatabase: AdBlockAllowListDatabase): AdBlockAllowListRepository

    @Binds
    abstract fun providesAllowListModel(sessionAllowListModel: SessionAllowListModel): AllowListModel

    @Binds
    abstract fun providesSslWarningPreferences(sessionSslWarningPreferences: SessionSslWarningPreferences): SslWarningPreferences

}
