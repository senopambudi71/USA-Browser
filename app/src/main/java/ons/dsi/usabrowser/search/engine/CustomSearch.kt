package ons.dsi.usabrowser.search.engine

import ons.dsi.usabrowser.R

/**
 * A custom search engine.
 */
class CustomSearch(queryUrl: String) : BaseSearchEngine(
    "file:///android_asset/a5gfastbrowser.png",
    queryUrl,
    R.string.search_engine_custom
)
