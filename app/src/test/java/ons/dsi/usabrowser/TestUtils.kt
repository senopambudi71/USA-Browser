@file:Suppress("NOTHING_TO_INLINE")

package ons.dsi.usabrowser

/**
 * Use to implement an unimplemented method.
 */
inline fun unimplemented(): Nothing {
    throw NotImplementedError("Not implemented")
}
