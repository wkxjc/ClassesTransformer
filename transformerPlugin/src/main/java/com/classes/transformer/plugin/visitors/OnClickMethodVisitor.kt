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
        methodVisitor.visitLineNumber(12, label1)
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null)
        methodVisitor.visitInsn(RETURN)
        val label2 = Label()
        methodVisitor.visitLabel(label2)
        methodVisitor.visitLocalVariable("this", "Lcom/classes/transformer/Test;", null, label0, label2, 0)
        methodVisitor.visitLocalVariable("v", "Landroid/view/View;", null, label0, label2, 1)
        methodVisitor.visitMaxs(2, 2)
    }
}