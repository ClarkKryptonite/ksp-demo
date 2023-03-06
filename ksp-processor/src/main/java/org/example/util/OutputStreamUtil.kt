package com.example.launchbase.util

import java.io.OutputStream

fun OutputStream.appendText(str: String) {
    write(str.toByteArray())
}

fun OutputStream.newLine(
    lineCount: Int = 1
) {
    var content = ""
    repeat(lineCount) {
        content += "\n"
    }
    appendText(content)
}

fun OutputStream.appendTabText(count: Int = 1, content: String) {
    var tabContent = ""
    repeat(count) {
        tabContent += "\t"
    }
    tabContent += content
    appendText(tabContent)
}