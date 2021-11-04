package com.classes.transformer.plugin

open class InjectConfig {
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
const val INJECT_EXTENSION_NAME = "injectConfig"