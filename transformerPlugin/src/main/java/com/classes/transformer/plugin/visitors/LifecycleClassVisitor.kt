package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.utils.MethodUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class LifecycleClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM5, cv) {
    private var className: String? = null
    private var superName: String? = null
    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (MethodUtils.isLifecycleMethod(superName, name)) {
            return LifecycleMethodVisitor(mv, className, name)
        }
        if (MethodUtils.isViewOnclickMethod(access, name, descriptor)) {
            // TODO
//            return OnClickMethodVisitor(mv)
        }
        return mv
    }

}