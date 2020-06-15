/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.backend.unsafe

import com.pega.mmock.backend.runSuspend
import kotlin.js.JsName
import kotlin.test.Test
import kotlinx.coroutines.delay

@OptIn(ExperimentalUnsignedTypes::class)
class DefaultInstanceTest {
    private interface A
    private class B

    @Suppress("unused_parameter")
    fun regularFunction(arg: Any) {
        // empty body
    }

    @Suppress("unused_parameter")
    suspend fun suspendFunction(arg: Any) {
        delay(1)
    }

    @Test
    @JsName("Can_be_created")
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
    }

    @Test
    @JsName("Can_be_passed_into_function")
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
    }

    @Test
    @JsName("Can_be_passed_into_suspend_function")
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
    }
}