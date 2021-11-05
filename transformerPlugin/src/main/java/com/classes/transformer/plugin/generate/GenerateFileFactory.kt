package com.classes.transformer.plugin.generate

import com.android.build.gradle.AppExtension
import com.classes.transformer.plugin.DebouncePlugin
import com.classes.transformer.plugin.utils.LogUtil
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * Created by Kevin 2021-11-05
 */
object GenerateFileFactory {
    private const val GENERATE_FILE_TASK_NAME = "generateDebounceFile"
    private const val GENERATED_FILE_NAME = "DebounceClickChecker.kt"
    private val PROJECT_BUILD_DIR = DebouncePlugin.project.buildDir
    private val GENERATED_FILE_SAVED_DIR = "$PROJECT_BUILD_DIR\\generated\\source\\debouncePlugin"

    fun addDebounceClickChecker() {
        insertGenerateFileTask()
        addGeneratedFileToSourceSets()
    }

    private fun insertGenerateFileTask() {
        LogUtil.log("Create gradle task: $GENERATE_FILE_TASK_NAME")
        DebouncePlugin.project.task(GENERATE_FILE_TASK_NAME) {
            val file = File(GENERATED_FILE_SAVED_DIR, GENERATED_FILE_NAME)
            file.parentFile.mkdirs()
            val writer = BufferedWriter(FileWriter(file))
            writer.use { it.write(debounceClickCheckerFileContent()) }
            LogUtil.log("$GENERATED_FILE_NAME has generated.")
        }
        LogUtil.log("Insert $GENERATE_FILE_TASK_NAME before first task: ${DebouncePlugin.project.tasks.first()}")
        DebouncePlugin.project.tasks.first().dependsOn(GENERATE_FILE_TASK_NAME)
    }

    private fun debounceClickCheckerFileContent(): String {
        return """
                   package ${DebouncePlugin.applicationId}.transformer
                   
                   import android.util.Log
                   import android.view.View
                   
                   object DebounceClickChecker {
                       var lastClickViewId = -1
                       var lastClickTime = 0L
                       fun needIntercept(view: View?): Boolean {
                           view ?: return false
                           val time = System.currentTimeMillis()
                           val intercept = view.id == lastClickViewId && time - lastClickTime < ${DebouncePlugin.debounceConfig.debounceIntervalTime}
                           if (!intercept) {
                               lastClickTime = time
                               lastClickViewId = view.id
                           }
                           val viewIdName = view.context.resources.getResourceEntryName(view.id)
                           Log.d("DebounceClickChecker", if (intercept) "Intercept quick click on ${'$'}viewIdName" else "User clicked ${'$'}viewIdName")
                           return intercept
                       }
                   }
               """.trimIndent()
    }

    /**
     * sourceSets {
     *     main {
     *         java {
     *             srcDirs += ['build/generated/source/transformer']
     *         }
     *     }
     * }
     */
    private fun addGeneratedFileToSourceSets() {
        val androidExtension = DebouncePlugin.project.extensions.getByType(AppExtension::class.java)
        androidExtension.sourceSets.getByName("main").java.run {
            setSrcDirs(srcDirs.plus(GENERATED_FILE_SAVED_DIR))
            LogUtil.log("$GENERATED_FILE_SAVED_DIR has added to sourceSets, srcDirs: $srcDirs")
        }
    }
}