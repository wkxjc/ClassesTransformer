package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.DebouncePlugin
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*


/**
 * Created by Kevin 2021-11-05
 */
class OnClickKotlinLambdaMethodVisitor(private val methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM5, methodVisitor) {
    override fun visitCode() {
        super.visitCode()
        methodVisitor.visitFieldInsn(
            GETSTATIC,
            "${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker",
            "INSTANCE",
            "L${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker;"
        )
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "${DebouncePlugin.applicationId.replace(".", "/")}/transformer/DebounceClickChecker", "needIntercept", "(Landroid/view/View;)Z", false)
        val label1 = Label()
        methodVisitor.visitJumpInsn(IFEQ, label1)
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitLabel(label1)
    }
}