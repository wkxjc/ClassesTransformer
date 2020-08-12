package com.classes.transformer.plugin.utils

import org.objectweb.asm.Opcodes

object MethodUtils {
    fun isPrivate(access: Int) = access and Opcodes.ACC_PRIVATE != 0

    fun isPublic(access: Int) = access and Opcodes.ACC_PUBLIC != 0

    fun isStatic(access: Int) = access and Opcodes.ACC_STATIC != 0

    fun isAbstract(access: Int) = access and Opcodes.ACC_ABSTRACT != 0

    fun isBridge(access: Int) = access and Opcodes.ACC_BRIDGE != 0

    fun isSynthetic(access: Int) = access and Opcodes.ACC_SYNTHETIC != 0

    fun isLifecycleMethod(superName: String?, name: String?) =
        !superName.isNullOrEmpty() && !name.isNullOrEmpty()
                && superName == "androidx/appcompat/app/AppCompatActivity"
                && (name.startsWith("onCreate")
                || name.startsWith("onStart")
                || name.startsWith("onResume")
                || name.startsWith("onPause")
                || name.startsWith("onStop")
                || name.startsWith("onDestroy")
                || name.startsWith("onRestart")
                || name.startsWith("onNewIntent"))

    fun isViewOnclickMethod(access: Int, name: String?, desc: String?) = isPublic(access) && !isStatic(access) && !isAbstract(access)
            && name == "onClick" && desc == "(Landroid/view/View;)V"

    fun isListViewOnItemOnclickMethod(access: Int, name: String, desc: String) = isPublic(access) && !isStatic(access) && !isAbstract(access)
            && name == "onItemClick" && desc == "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V"

}

