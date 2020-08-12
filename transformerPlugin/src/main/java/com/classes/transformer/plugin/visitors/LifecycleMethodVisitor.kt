package com.classes.transformer.plugin.visitors

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LifecycleMethodVisitor(mv: MethodVisitor, private val className: String, private val methodName: String) :
    MethodVisitor(Opcodes.ASM5, mv) {
    override fun visitCode() {
        super.visitCode()
        mv.visitLdcInsn("Lifecycle Plugin")
        mv.visitLdcInsn("$className --> $methodName")
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/util/Log",
            "i",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        mv.visitInsn(Opcodes.POP)
    }
}