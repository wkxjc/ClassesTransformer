package com.classes.transformer.transformer

import android.util.Log
import android.view.View

object DebounceClickChecker {
    var lastClickViewId = -1
    var lastClickTime = 0L
    fun needIntercept(view: View?): Boolean {
        view ?: return false
        val time = System.currentTimeMillis()
        val intercept = view.id == lastClickViewId && time - lastClickTime < 500
        if (!intercept) {
            lastClickTime = time
            lastClickViewId = view.id
        }
        val viewIdName = view.context.resources.getResourceEntryName(view.id)
        Log.d("DebounceClickChecker", if (intercept) "Intercept quick click on $viewIdName" else "User clicked $viewIdName")
        return intercept
    }
}