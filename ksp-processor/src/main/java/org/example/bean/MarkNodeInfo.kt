package org.example.bean

data class MarkNodeInfo(
    val packageName: String,
    val className: String,
    val priority: Int = 0,
    val functionName: String = "call"
)
