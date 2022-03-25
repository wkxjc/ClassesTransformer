package com.classes.transformer.plugin.visitors

import com.classes.transformer.plugin.DebouncePlugin
import com.classes.transformer.plugin.utils.*
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class DebounceClassNode : ClassNode(Opcodes.ASM9) {
    // if there's no method changed, then it's not necessary to rewrite this class file.
    var methodChanged = false
    private val ViewDescriptor = "Landroid/view/View;"
    private val debounceCheckClass: String = "${DebouncePlugin.applicationId}.transformer.DebounceClickChecker"
    private val formattedDoubleCheckClass: String
        get() = debounceCheckClass.replace(".", "/")
    private val debounceCheckMethodName: String = "allow"
    private val debounceCheckMethodDescriptor: String = "(Landroid/view/View;)Z"

    fun modifyClass() {
        val onClickMethods = mutableListOf<String>()
        // Collect normal OnClick method in class
        val onClickMethod = methods.filter { isOnClickMethod(it) }
        onClickMethods.addAll(onClickMethod.map { it.nameWithDesc })
        LogUtil.log("methods: ${methods.size}, onClickMethod: ${onClickMethod.size}")
        // Collect lambda expressions in every single method
        methods.forEach {
            val lambdaExpressions = it.findLambda()
            val onClickExpressions = lambdaExpressions.filter { it.isOnClickMethod() }
            LogUtil.log("lambdaExpressions: ${lambdaExpressions.size}, onClickExpressions: ${onClickExpressions.size}")
            onClickMethods.addAll(onClickExpressions.map { it.handleNameWithDesc() })
        }
        LogUtil.log("onClickMethods size: " + onClickMethods.size + ", onClickMethods: ${onClickMethods.joinToString()}")
        if (onClickMethods.isEmpty()) return
        methods.forEach { methodNode ->
            LogUtil.log("methodNameWithDesc: ${methodNode.nameWithDesc}")
            if (onClickMethods.contains(methodNode.nameWithDesc)) {
                val argumentTypes = Type.getArgumentTypes(methodNode.desc)
                // Find index of view argument
                val viewArgumentIndex = argumentTypes?.indexOfFirst { it.descriptor == ViewDescriptor } ?: -1
                if (viewArgumentIndex < 0) return@forEach
                if (methodNode.instructions == null) return@forEach
                // Start insert debounce check
                val list = InsnList()
                val visitPosition = getVisitPosition(argumentTypes, viewArgumentIndex, methodNode.isStatic)
                LogUtil.log("visitPosition: $visitPosition")
                list.add(VarInsnNode(Opcodes.ALOAD, visitPosition))
                list.add(MethodInsnNode(Opcodes.INVOKESTATIC, formattedDoubleCheckClass, debounceCheckMethodName, debounceCheckMethodDescriptor))
                val labelNode = LabelNode()
                list.add(JumpInsnNode(Opcodes.IFNE, labelNode))
                list.add(InsnNode(Opcodes.RETURN))
                list.add(labelNode)
                methodNode.instructions.insert(list)
                LogUtil.log("Rewrite method: ${methodNode.nameWithDesc}")
                if (!methodChanged) methodChanged = true
            }
        }
    }
}