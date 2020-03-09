package com.github.virelion.mmock.backend.unsafe

import com.github.virelion.mmock.backend.runSuspend
import kotlinx.coroutines.delay
import kotlin.test.Test

class DefaultInstanceTest {
    private interface A
    private class B
    private sealed class C
    private abstract class D

    fun regularFunction(arg: Any) {
        // empty body
    }

    suspend fun suspendFunction(arg: Any) {
        delay(1)
    }

    @Test
    fun `Can be created`() {
        defaultInstance<Boolean>()
        defaultInstance<Int>()
        defaultInstance<Short>()
        defaultInstance<Long>()
        defaultInstance<Byte>()
        defaultInstance<UInt>()
        defaultInstance<UShort>()
        defaultInstance<ULong>()
        defaultInstance<UByte>()
        defaultInstance<Double>()
        defaultInstance<Char>()
        defaultInstance<String>()
        defaultInstance<A>()
        defaultInstance<B>()
        defaultInstance<C>()
        defaultInstance<D>()
    }

    @Test
    fun `Can be passed into function`() {
        regularFunction(defaultInstance<Boolean>())
        regularFunction(defaultInstance<Int>())
        regularFunction(defaultInstance<Short>())
        regularFunction(defaultInstance<Long>())
        regularFunction(defaultInstance<Byte>())
        regularFunction(defaultInstance<UInt>())
        regularFunction(defaultInstance<UShort>())
        regularFunction(defaultInstance<ULong>())
        regularFunction(defaultInstance<UByte>())
        regularFunction(defaultInstance<Double>())
        regularFunction(defaultInstance<Char>())
        regularFunction(defaultInstance<String>())
        regularFunction(defaultInstance<A>())
        regularFunction(defaultInstance<B>())
        regularFunction(defaultInstance<C>())
        regularFunction(defaultInstance<D>())
    }

    @Test
    fun `Can be passed into suspend function`() = runSuspend {
        suspendFunction(defaultInstance<Boolean>())
        suspendFunction(defaultInstance<Int>())
        suspendFunction(defaultInstance<Short>())
        suspendFunction(defaultInstance<Long>())
        suspendFunction(defaultInstance<Byte>())
        suspendFunction(defaultInstance<UInt>())
        suspendFunction(defaultInstance<UShort>())
        suspendFunction(defaultInstance<ULong>())
        suspendFunction(defaultInstance<UByte>())
        suspendFunction(defaultInstance<Double>())
        suspendFunction(defaultInstance<Char>())
        suspendFunction(defaultInstance<String>())
        suspendFunction(defaultInstance<A>())
        suspendFunction(defaultInstance<B>())
        suspendFunction(defaultInstance<C>())
        suspendFunction(defaultInstance<D>())
    }
}