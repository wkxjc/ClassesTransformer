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
        if (MethodUtils.isViewOnclickMethod(access, name, descriptor)) {
            LogUtil.log("Find method: $name")
            methodChanged = true
            return OnClickMethodVisitor(mv)
        }
        return mv
    }
}