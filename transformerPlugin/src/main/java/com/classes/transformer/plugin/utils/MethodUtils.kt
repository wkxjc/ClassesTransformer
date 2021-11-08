package com.classes.transformer.plugin.utils

import org.objectweb.asm.Opcodes

/**
 * Created by Kevin 2021-11-05
 */
object MethodUtils {
    fun isPrivate(access: Int) = access and Opcodes.ACC_PRIVATE != 0

    fun isPublic(access: Int) = access and Opcodes.ACC_PUBLIC != 0

    fun isStatic(access: Int) = access and Opcodes.ACC_STATIC != 0

    fun isAbstract(access: Int) = access and Opcodes.ACC_ABSTRACT != 0

    fun isBridge(access: Int) = access and Opcodes.ACC_BRIDGE != 0

    fun isSynthetic(access: Int) = access and Opcodes.ACC_SYNTHETIC != 0

    fun isViewOnclickMethod(access: Int, name: String?, desc: String?): Boolean {
        LogUtil.log("Normal onclickListener: ${(isPublic(access) && !isStatic(access) && !isAbstract(access) && name == "onClick" && desc == "(Landroid/view/View;)V")}\n")
        return isPublic(access) && !isStatic(access) && !isAbstract(access) && name == "onClick" && desc == "(Landroid/view/View;)V"
    }

    fun isKotlinLambdaViewOnclickMethod(access: Int, name: String?, desc: String?): Boolean {
        LogUtil.log("Kotlin lambda: ${(!isPublic(access) && isStatic(access) && !isAbstract(access) && name?.contains("${'$'}lambda") == true && desc == "(Landroid/view/View;)V")}")
        return !isPublic(access) && isStatic(access) && !isAbstract(access) && name?.contains("${'$'}lambda") == true && desc == "(Landroid/view/View;)V"
    }
}

