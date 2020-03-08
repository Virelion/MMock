package com.github.virelion.mmock.scenarios

import com.github.virelion.mmock.MMockRecordingException
import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.dsl.*
import com.github.virelion.mmock.samples.ExampleInterface
import com.github.virelion.mmock.verifyFailed
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SuspendArgumentMatcherScenarios {
    @Test
    @JsName("Equals_matcher_in_mock")
    fun `Equals matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunction(eq(1)) } returns 1
        every { exampleInterface.suspendFunction(eq(2)) } returns 2

        assertEquals(1, exampleInterface.suspendFunction(1))
        assertEquals(2, exampleInterface.suspendFunction(2))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendFunction(3) }
    }

    @Test
    @JsName("Any_matcher_in_mock")
    fun `Any matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunction(any()) } returns 1

        assertEquals(1, exampleInterface.suspendFunction(1))
        assertEquals(1, exampleInterface.suspendFunction(2))
        assertEquals(1, exampleInterface.suspendFunction(Int.MAX_VALUE))
    }

    @Test
    @JsName("InstanceOf_matcher_in_mock")
    fun `InstanceOf matcher in mock`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunctionAny(instanceOf<String>()) } returns "String"
        every { exampleInterface.suspendFunctionAny(instanceOf<Int>()) } returns "Int"

        assertEquals("String", exampleInterface.suspendFunctionAny(""))
        assertEquals("Int", exampleInterface.suspendFunctionAny(1))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendFunctionAny(false) }
    }

    @Test
    @JsName("Equals_matcher_in_verification")
    fun `Equals matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunction(any()) } returns 1

        exampleInterface.suspendFunction(1)
        exampleInterface.suspendFunction(1)
        exampleInterface.suspendFunction(2)
        exampleInterface.suspendFunction(3)

        verify {
            exampleInterface.suspendFunction(eq(1)) called twice
            exampleInterface.suspendFunction(eq(2)) called once
            exampleInterface.suspendFunction(eq(3)) called once
            exampleInterface.suspendFunction(eq(4)) called never
        }

        verifyFailed { exampleInterface.suspendFunction(eq(1)) called once }
        verifyFailed { exampleInterface.suspendFunction(eq(2)) called twice }
        verifyFailed { exampleInterface.suspendFunction(eq(2)) called twice }
        verifyFailed { exampleInterface.suspendFunction(eq(4)) called once }
    }

    @Test
    @JsName("Any_matcher_in_verification")
    fun `Any matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunctionAny(any()) } returns 1

        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(2)
        exampleInterface.suspendFunctionAny(3)

        verify {
            exampleInterface.suspendFunctionAny(any()) called times(4)
        }

        verifyFailed { exampleInterface.suspendFunctionAny(any()) called times(3) }
        verifyFailed { exampleInterface.suspendFunctionAny(any()) called times(2) }
        verifyFailed { exampleInterface.suspendFunctionAny(any()) called times(1) }
        verifyFailed { exampleInterface.suspendFunctionAny(any()) called times(0) }
    }

    @Test
    @JsName("InstanceOf_matcher_in_verification")
    fun `InstanceOf matcher in verification`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendFunctionAny(any()) } returns Unit

        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny(1)
        exampleInterface.suspendFunctionAny("A")
        exampleInterface.suspendFunctionAny("B")
        exampleInterface.suspendFunctionAny(false)

        verify {
            exampleInterface.suspendFunctionAny(instanceOf<Int>()) called times(3)
            exampleInterface.suspendFunctionAny(instanceOf<String>()) called twice
            exampleInterface.suspendFunctionAny(instanceOf<Boolean>()) called once
        }
        verifyFailed { exampleInterface.suspendFunctionAny(instanceOf<Int>()) called times(4) }
        verifyFailed { exampleInterface.suspendFunctionAny(instanceOf<String>()) called once }
        verifyFailed { exampleInterface.suspendFunctionAny(instanceOf<Boolean>()) called twice }
    }

    @Test
    @JsName("Multiple_argument_matchers___complex_mock_scenario")
    fun `Multiple argument matchers - complex mock scenario`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendMultipleArgs(eq(1), eq(2), eq(3)) } returns 1
        every { exampleInterface.suspendMultipleArgs(eq(1), eq(2), eq(4)) } returns 2
        every { exampleInterface.suspendMultipleArgs(eq(1), eq(3), eq(4)) } returns 3
        every { exampleInterface.suspendMultipleArgs(eq("STR"), eq(3), eq(4)) } returns 4

        assertEquals(1, exampleInterface.suspendMultipleArgs(1, 2, 3))
        assertEquals(2, exampleInterface.suspendMultipleArgs(1, 2, 4))
        assertEquals(3, exampleInterface.suspendMultipleArgs(1, 3, 4))
        assertEquals(4, exampleInterface.suspendMultipleArgs("STR", 3, 4))

        assertFailsWith<NoMethodStubException> { exampleInterface.suspendMultipleArgs(0, 0, 0) }
    }

    @Test
    @JsName("Multiple_argument_matchers___complex_verification_scenario")
    fun `Multiple argument matchers - complex verification scenario`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendMultipleArgs(any(), any(), any()) } returns 1

        exampleInterface.suspendMultipleArgs(1, 2, 3)
        exampleInterface.suspendMultipleArgs("", 2, 3)
        exampleInterface.suspendMultipleArgs("", "", 3)
        exampleInterface.suspendMultipleArgs(1, "", 3)
        exampleInterface.suspendMultipleArgs(1, "", false)

        verify {
            exampleInterface.suspendMultipleArgs(any(), any(), any()) called times(5)
            exampleInterface.suspendMultipleArgs(instanceOf<Int>(), any(), any()) called times(3)
            exampleInterface.suspendMultipleArgs(instanceOf<String>(), any(), any()) called times(2)
            exampleInterface.suspendMultipleArgs(instanceOf<String>(), instanceOf<String>(), any()) called once
            exampleInterface.suspendMultipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) called never
            exampleInterface.suspendMultipleArgs(any(), any(), eq(false)) called once
        }

        verifyFailed { exampleInterface.suspendMultipleArgs(any(), any(), any()) called once }
        verifyFailed { exampleInterface.suspendMultipleArgs(instanceOf<Int>(), any(), any()) called times(2) }
        verifyFailed { exampleInterface.suspendMultipleArgs(instanceOf<String>(), any(), any()) called times(3) }
        verifyFailed { exampleInterface.suspendMultipleArgs(instanceOf<String>(), instanceOf<String>(), any()) called never }
        verifyFailed { exampleInterface.suspendMultipleArgs(instanceOf<String>(), instanceOf<String>(), instanceOf<String>()) called once }
        verifyFailed { exampleInterface.suspendMultipleArgs(any(), any(), eq(false)) called never }
    }

    @Test
    @JsName("Implicit_equals_argument_matcher_during_recording")
    fun `Implicit equals argument matcher during recording`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        every { exampleInterface.suspendMultipleArgs(1, 2, 3) } returns 1

        assertEquals(1, exampleInterface.suspendMultipleArgs(1, 2, 3))
        assertFailsWith<NoMethodStubException> { exampleInterface.suspendMultipleArgs(0, 0, 0) }

        verify {
            exampleInterface.suspendMultipleArgs(1, 2, 3) called once
            exampleInterface.suspendMultipleArgs(3, 2, 1) called never
        }

        verifyFailed { exampleInterface.suspendMultipleArgs(1, 2, 3) called never }
        verifyFailed { exampleInterface.suspendMultipleArgs(3, 2, 1) called once }
    }

    @Test
    @JsName("Error_is_thrown_in_case_of_mixed_matcher_usage")
    fun `Error is thrown in case of mixed matcher usage`() = withMMock {
        val exampleInterface = mmock<ExampleInterface>()

        assertFailsWith<MMockRecordingException> {
            every { exampleInterface.suspendMultipleArgs(1, any(), 3) } returns 1
        }

        assertFailsWith<MMockRecordingException> {
            verify {
                exampleInterface.suspendMultipleArgs(any(), 2, 3) called once
            }
        }
    }
}