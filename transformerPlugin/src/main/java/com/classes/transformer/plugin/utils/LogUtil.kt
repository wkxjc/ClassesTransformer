package com.classes.transformer.plugin.utils

import com.classes.transformer.plugin.DebouncePlugin

/**
 * Created by Kevin 2020-08-14
 * Log util controlled by [DebouncePlugin.debounceConfig.isDebug]
 */
object LogUtil {
    fun log(content: String) {
        if (!DebouncePlugin.debounceConfig.isDebug) {
            return
        }
        println(content)
    }
}