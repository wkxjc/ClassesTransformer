package com.classes.transformer.plugin.visitors

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class OnClickMethodVisitor(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM5, mv) {
    override fun visitCode() {
        super.visitCode()
    }
}