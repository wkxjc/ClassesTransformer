package com.classes.transformer.plugin.utils

import java.io.File

/**
 * Created by Kevin 2021-11-05
 */
fun File.traverse(): MutableList<File> = mutableListOf<File>().apply {
    if (isDirectory) listFiles()?.forEach { addAll(it.traverse()) }
    else add(this@traverse)
}