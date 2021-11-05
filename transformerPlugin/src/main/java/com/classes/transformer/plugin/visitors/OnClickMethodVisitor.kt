package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.DebouncePlugin
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

class OnClickMethodVisitor(private val methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM5, methodVisitor) {
    override fun visitCode() {
        super.visitCode()

        val label0 = Label()
        methodVisitor.visitLabel(label0)
        methodVisitor.visitLineNumber(21, label0)
        methodVisitor.visitFieldInsn(
            GETSTATIC,
            "${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker",
            "INSTANCE",
            "L${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker;"
        )
        methodVisitor.visitVarInsn(ALOAD, 1)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker", "needIntercept", "(Landroid/view/View;)Z", false)
        val label1 = Label()
        methodVisitor.visitJumpInsn(IFEQ, label1)
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitLabel(label1)
    }
}