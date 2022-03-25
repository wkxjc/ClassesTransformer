package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.utils.LogUtil
import com.classes.transformer.plugin.utils.MethodUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by Kevin 2021-11-05
 */
class DebounceClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM5, cv) {
    // if there's no method changed, then it's not necessary to rewrite this class file.
    var methodChanged = false

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        LogUtil.log("Check method: name = $name\n desc = $descriptor\n signature = $signature\n isPublic = ${MethodUtils.isPublic(access)}\n isStatic = ${MethodUtils.isStatic(access)}\n isAbstract = ${MethodUtils.isAbstract(access)}\n isSynthetic = ${MethodUtils.isSynthetic(access)}\n isBridge = ${MethodUtils.isBridge(access)}\n isPrivate = ${MethodUtils.isPrivate(access)}")
        if (MethodUtils.isViewOnclickMethod(access, name, descriptor)) {
            LogUtil.log("Find method: $name")
            methodChanged = true
            return OnClickMethodVisitor(mv)
        }
        if (MethodUtils.isKotlinLambdaViewOnclickMethod(access, name, descriptor)) {
            LogUtil.log("Find Kotlin lambda method: $name")
            methodChanged = true
            return OnClickKotlinLambdaMethodVisitor(mv)
        }
        return mv
    }
}