package ons.dsi.usabrowser.extensions

import android.content.ClipData
import android.content.ClipboardManager

/**
 * Copies the [text] to the clipboard with the label `URL`.
 */
fun ClipboardManager.copyToClipboard(text: String) {
    var primaryClip = ClipData.newPlainText("URL", text)
}
