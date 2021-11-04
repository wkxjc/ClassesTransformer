package com.classes.transformer.plugin

object DebounceClickChecker {
    var lastClickViewId = -1
    var lastClickTime = 0L
    fun needIntercept(viewId: Int): Boolean {
        val time = System.currentTimeMillis()
        val intercept = viewId == lastClickViewId && time - lastClickTime < 500
        if (!intercept) {
            lastClickTime = time
            lastClickViewId = viewId
        }
        return intercept
    }
}