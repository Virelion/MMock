package com.github.virelion.mmock.compiler.util

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

fun MessageCollector.p(message: Any?) {
    report(CompilerMessageSeverity.WARNING, "[${System.nanoTime()}] $message")
    Thread.sleep(1L)
}

fun <T> MessageCollector.report(label: String, iterable: Iterable<T>, action: MessageCollector.(T) -> Unit) {
    p("<$label count='${iterable.count()}' >")
    iterable.forEach {
        action(it)
    }
    p("</$label>")
}

fun <T> MessageCollector.report(label: String, array: Array<out T>, action: MessageCollector.(T) -> Unit) {
    p("<$label count='${array.size}' >")
    array.forEach {
        action(it)
    }
    p("</$label>")
}

fun MessageCollector.showChildren(parent: PsiElement) {
    p("<${parent.javaClass}>")
    report("children", parent.children) {
        p(it.javaClass)
    }
    p("</${parent.javaClass}>")
}