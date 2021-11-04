package com.classes.transformer.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.classes.transformer.plugin.utils.LogUtil
import com.classes.transformer.plugin.utils.traverse
import com.classes.transformer.plugin.visitors.DebounceClassVisitor
import org.apache.commons.io.FileUtils

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileOutputStream

class DebounceTransform : Transform() {
    override fun getName(): String = this.javaClass.name

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation?) {
        LogUtil.log("DebounceTransform start")
        transformInvocation?.inputs?.forEach {
            LogUtil.log("TransformInput: $it")
            it.directoryInputs.forEach { input ->
                transformDirectoryInput(input)
                FileUtils.copyDirectory(input.file, transformInvocation.outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY))
            }
            it.jarInputs.forEach { input ->
                FileUtils.copyFile(input.file, transformInvocation.outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR))
            }
        }


        LogUtil.log("DebounceTransform finish")
    }

    private fun transformDirectoryInput(input: DirectoryInput) {
        input.file.traverse().filter { classesFilter(it) }.forEach { file ->
            LogUtil.log("Find file: $file")
            val classReader = ClassReader(file.readBytes())
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            classReader.accept(DebounceClassVisitor(classWriter), ClassReader.EXPAND_FRAMES)
            FileOutputStream(file.path).use {
                it.write(classWriter.toByteArray())
            }
        }
    }

    private fun classesFilter(file: File) = file.name.endsWith(".class") && !file.name.startsWith("R\$") && !file.name.startsWith("android")
            && file.name != "R.class" && file.name != "BuildConfig.class"
}