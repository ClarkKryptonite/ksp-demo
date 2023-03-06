package org.example

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class MarkNodeTransit(val jsonStr: String)
