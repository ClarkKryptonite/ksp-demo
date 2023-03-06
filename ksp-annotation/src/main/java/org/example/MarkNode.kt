package org.example

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class MarkNode(
    val name: String,
    val priority: Int = 0
)
