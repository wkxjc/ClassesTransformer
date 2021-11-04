package com.classes.transformer.plugin

import com.android.build.gradle.AppExtension
import com.classes.transformer.plugin.utils.LogUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

class DebouncePlugin : Plugin<Project> {
    companion object {
        // injectExtension config in build.gradle
        lateinit var injectConfig: InjectConfig
    }

    override fun apply(project: Project) {
        injectConfig = project.extensions.create(INJECT_EXTENSION_NAME, InjectConfig::class.java)
        LogUtil.log("DebouncePlugin coming, injectConfig: $injectConfig")
        project.extensions.getByType(AppExtension::class.java).registerTransform(DebounceTransform())
    }
}