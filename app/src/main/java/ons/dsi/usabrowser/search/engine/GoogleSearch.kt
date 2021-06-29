package ons.dsi.usabrowser.search.engine

import ons.dsi.usabrowser.R

/**
 * The Google search engine.
 *
 * See https://www.google.com/images/srpr/logo11w.png for the icon.
 */
class GoogleSearch : BaseSearchEngine(
    "file:///android_asset/duckduckgo.png",
    "https://duckduckgo.com/?t=a5gfastbrowser&q=",
    R.string.search_engine_duckduckgo

)
