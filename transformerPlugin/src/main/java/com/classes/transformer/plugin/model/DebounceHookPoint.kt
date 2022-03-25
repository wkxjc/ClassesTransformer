package com.classes.transformer.plugin.model

data class DebounceHookPoint(
    val interfaceName: String,
    val methodName: String,
    val methodSign: String,
) {
    val interfaceSignSuffix = "L$interfaceName;"
}

val hookPoints = listOf(
    DebounceHookPoint(
        interfaceName = "android/view/View\$OnClickListener",
        methodName = "onClick",
        methodSign = "onClick(Landroid/view/View;)V"
    )
)