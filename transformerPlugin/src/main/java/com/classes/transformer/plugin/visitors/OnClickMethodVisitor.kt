package com.classes.transformer.plugin.visitors

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

import org.objectweb.asm.Label


class OnClickMethodVisitor(val methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM5, methodVisitor) {
    override fun visitCode() {
        super.visitCode()
        val label0 = Label()
        methodVisitor.visitLabel(label0)
        methodVisitor.visitLineNumber(11, label0)
        methodVisitor.visitFieldInsn(GETSTATIC, "com/classes/transformer/DebounceClickChecker", "INSTANCE", "Lcom/classes/transformer/DebounceClickChecker;")
        methodVisitor.visitVarInsn(ALOAD, 1)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getId", "()I", false)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/classes/transformer/DebounceClickChecker", "needIntercept", "(I)Z", false)
        val label1 = Label()
        methodVisitor.visitJumpInsn(IFEQ, label1)
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitLabel(label1)
    }
}