package com.classes.transformer.plugin

import com.android.build.gradle.AppExtension
import com.classes.transformer.plugin.utils.LogUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import javax.lang.model.element.ExecutableElement

class DebouncePlugin : Plugin<Project> {
    companion object {
        // injectExtension config in build.gradle
        lateinit var injectConfig: InjectConfig
        lateinit var project: Project
    }

    override fun apply(project: Project) {
        DebouncePlugin.project = project
        injectConfig = project.extensions.create(INJECT_EXTENSION_NAME, InjectConfig::class.java)
        project.task("insertFile") {
            LogUtil.log("DebouncePlugin coming, injectConfig: $injectConfig")
            val filePath = "${project.buildDir}/generated/source/com/classes/transformer"
            val file = File(filePath, "DebounceClickChecker.kt")
            file.parentFile.mkdirs()
            val writer = BufferedWriter(FileWriter(file))
            writer.use {
                it.write(
                    """
                package com.classes.transformer

                object DebounceClickChecker {
                    var lastClickViewId = -1
                    var lastClickTime = 0L
                    fun needIntercept(viewId: Int): Boolean {
                        val time = System.currentTimeMillis()
                        val intercept = viewId == lastClickViewId && time - lastClickTime < ${injectConfig.debounceIntervalTime}
                        if (!intercept) {
                            lastClickTime = time
                            lastClickViewId = viewId
                        }
                        return intercept
                    }
                }
                
            """.trimIndent()
                )
            }
        }
        project.tasks.forEach {
            if(it.name == "assemble"){
                it.dependsOn("insertFile")
            }
        }

        LogUtil.log("DebouncePlugin coming, injectConfig: $injectConfig")
        project.extensions.getByType(AppExtension::class.java).registerTransform(DebounceTransform())
    }
}