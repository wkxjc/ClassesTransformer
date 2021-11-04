package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.utils.LogUtil
import com.classes.transformer.plugin.utils.MethodUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class DebounceClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM5, cv) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (MethodUtils.isViewOnclickMethod(access, name, descriptor)) {
            LogUtil.log("Find method: $name")
            return OnClickMethodVisitor(mv)
        }
        return mv
    }
}