package com.classes.transformer.plugin

/**
 * Created by Kevin 2021-11-05
 */
open class DebounceConfig {
    /** display inject plugin debug log if true */
    @JvmField
    var isDebug: Boolean = true

    /** debounce OnClickListener if true */
    @JvmField
    var isDebounceClick: Boolean = true

    /** interval time used to intercept debounce click. Unit: ms */
    @JvmField
    var debounceIntervalTime: Long = 500L

    override fun toString(): String {
        return "isDebug = $isDebug, isDebounceClick = $isDebounceClick, debounceIntervalTime = $debounceIntervalTime"
    }
}

/** extension name in build.gradle */
const val EXTENSION_PLUGIN_NAME = "debounceConfig"