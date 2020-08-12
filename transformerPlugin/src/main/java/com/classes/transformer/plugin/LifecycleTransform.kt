package com.classes.transformer.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.classes.transformer.plugin.utils.traverse

class LifecycleTransform : Transform() {
    override fun getName(): String = this.javaClass.name

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.PROJECT_ONLY

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation?) {
        transformInvocation?.inputs?.forEach {
            it.directoryInputs.forEach {
                it.file.traverse().filter { it.name.endsWith(".class") }.forEach {
                    println("find class: ${it.name}")
                }
            }
        }
    }
}