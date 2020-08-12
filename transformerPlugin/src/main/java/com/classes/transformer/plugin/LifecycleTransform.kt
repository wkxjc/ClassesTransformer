package com.classes.transformer.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.classes.transformer.plugin.utils.traverse
import com.classes.transformer.plugin.visitors.LifecycleClassVisitor
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileOutputStream

class LifecycleTransform : Transform() {
    override fun getName(): String = this.javaClass.name

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.PROJECT_ONLY

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation?) {
        transformInvocation?.inputs?.forEach {
            it.directoryInputs.forEach { input ->
                input.file.traverse().filter { it.name.endsWith(".class") }.forEach { file ->
                    val classReader = ClassReader(file.readBytes())
                    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    val classVisitor = LifecycleClassVisitor(classWriter)
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    FileOutputStream(file.path).use {
                        it.write(classWriter.toByteArray())
                    }
                }
                FileUtils.copyDirectory(input.file, transformInvocation.outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY))
            }
            it.jarInputs.forEach { input ->
                FileUtils.copyFile(input.file, transformInvocation.outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR))
            }
        }
    }
}