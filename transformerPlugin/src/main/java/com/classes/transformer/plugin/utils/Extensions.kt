package com.classes.transformer.plugin.utils

import com.classes.transformer.plugin.model.hookPoints
import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InvokeDynamicInsnNode
import org.objectweb.asm.tree.MethodNode
import java.io.File

/**
 * Created by Kevin 2021-11-05
 */
fun File.traverse(): MutableList<File> = mutableListOf<File>().apply {
    if (isDirectory) listFiles()?.forEach { addAll(it.traverse()) }
    else add(this@traverse)
}

fun ClassNode.isOnClickMethod(methodNode: MethodNode): Boolean {
    if (interfaces.isNullOrEmpty()) return false
    LogUtil.log("interfaces: ${interfaces.joinToString()}")
    hookPoints.forEach {
        if (interfaces.contains(it.interfaceName) && methodNode.nameWithDesc == it.methodSign) {
            return true
        }
    }
    return false
}

val MethodNode.nameWithDesc: String
    get() = name + desc

val MethodNode.isStatic: Boolean
    get() = access and Opcodes.ACC_STATIC != 0

fun MethodNode.findLambda(): List<InvokeDynamicInsnNode> = instructions.filterIsInstance<InvokeDynamicInsnNode>()

fun InvokeDynamicInsnNode.isOnClickMethod(): Boolean {
    LogUtil.log("name: $name, desc: $desc, bsmArgs: ${bsmArgs?.joinToString()}")
    return hookPoints.firstOrNull { name == it.methodName && desc.endsWith(it.interfaceSignSuffix) && bsmArgs[1] != null } != null
}

fun InvokeDynamicInsnNode.handleNameWithDesc(): String = (bsmArgs[1] as Handle).let { it.name + it.desc }

fun getVisitPosition(
    argumentTypes: Array<Type>,
    parameterIndex: Int,
    isStaticMethod: Boolean
): Int {
    if (parameterIndex < 0 || parameterIndex >= argumentTypes.size) {
        throw Error("getVisitPosition error")
    }
    return if (parameterIndex == 0) {
        if (isStaticMethod) {
            0
        } else {
            1
        }
    } else {
        getVisitPosition(
            argumentTypes,
            parameterIndex - 1,
            isStaticMethod
        ) + argumentTypes[parameterIndex - 1].size
    }
}