package com.classes.transformer.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LifecyclePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("LifecyclePlugin kotlin version start")
        val android = project.extensions.getByType(AppExtension::class.java)
        android.registerTransform(LifecycleTransform())
    }
}