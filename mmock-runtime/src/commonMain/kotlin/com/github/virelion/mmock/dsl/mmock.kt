package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.backend.runSuspend
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.samples.ExampleMock

@MMockDSL
inline fun <reified T> MMockContext.mmock(): T {
    return when (T::class) {
        ExampleInterface::class -> ExampleMock(this)
        else -> throw IllegalArgumentException("ExampleInterface only")
    } as T
}

fun withMMock(block: suspend MMockContext.() -> Unit): Unit = runSuspend {
    MMockContext().apply {
        block()
    }
    Unit
}