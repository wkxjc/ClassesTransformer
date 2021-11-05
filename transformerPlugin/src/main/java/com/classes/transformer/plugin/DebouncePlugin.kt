package com.classes.transformer.plugin

import com.android.build.gradle.AppExtension
import com.classes.transformer.plugin.generate.GenerateFileFactory
import com.classes.transformer.plugin.utils.LogUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Kevin 2021-11-05
 */
class DebouncePlugin : Plugin<Project> {
    companion object {
        lateinit var project: Project

        // injectExtension config in build.gradle
        lateinit var debounceConfig: DebounceConfig

        lateinit var applicationId: String
    }

    override fun apply(project: Project) {
        DebouncePlugin.project = project
        debounceConfig = project.extensions.create(EXTENSION_PLUGIN_NAME, DebounceConfig::class.java)
        val androidExtension = DebouncePlugin.project.extensions.getByType(AppExtension::class.java)
        applicationId = androidExtension.defaultConfig.applicationId!!
        LogUtil.log("DebouncePlugin: applicationId = $applicationId, debounceConfig = $debounceConfig")
        if (!debounceConfig.isDebounceClick) return
        GenerateFileFactory.addDebounceClickChecker()
        project.extensions.getByType(AppExtension::class.java).registerTransform(DebounceTransform())
    }
}