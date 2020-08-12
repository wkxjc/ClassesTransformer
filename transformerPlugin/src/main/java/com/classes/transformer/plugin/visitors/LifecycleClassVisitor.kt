package com.classes.transformer.plugin.visitors

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
        className ?: return mv
        superName ?: return mv
        name ?: return mv
        if (superName == "androidx/appcompat/app/AppCompatActivity") {
            if (name.startsWith("onCreate")) {
                return LifecycleMethodVisitor(mv, className ?: "", name)
            }
        }
        return mv
    }

}