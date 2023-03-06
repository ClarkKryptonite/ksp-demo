package org.example

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY)
annotation class MarkNodeTransit(val jsonStr: String)
