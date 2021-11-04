package com.classes.transformer.plugin.utils

import com.classes.transformer.plugin.DebouncePlugin

/**
 * Created by Kevin 2020-08-14
 * Log util controlled by [InjectConfig.isDebug]
 */
object LogUtil {
    fun log(content: String) {
        if (!DebouncePlugin.injectConfig.isDebug) return
        println(content)
    }
}