package com.github.virelion.mmock.backend

import com.github.virelion.mmock.NoMethodStubException
import com.github.virelion.mmock.backend.stack.MethodElement
import com.github.virelion.mmock.backend.unsafe.defaultInstance
import com.github.virelion.mmock.dsl.MMockContext

class MockContainer(
        val objectMock: ObjectMock
) {
    val context: MMockContext
        get() = objectMock.mMockContext

    val regular: FunctionRegistry<FunctionMock<*>> = FunctionRegistry()

    inline fun <reified T> invoke(name: String, vararg args: Any? = arrayOf()): T {
        when (context.state) {
            MMockContext.State.RECORDING -> {
                context.recordingStack?.add(MethodElement(name, objectMock, args))
                return defaultInstance()
            }
            MMockContext.State.INVOKING -> {
                return regular[name]
                        .firstOrNull { it.verificationFunction.verify(args) }
                        ?.apply {
                            context.invocationLogRecord.add(
                                    InvocationLogRecord(
                                            objectMock = objectMock,
                                            methodName = name,
                                            args = args
                                    )
                            )
                        }
                        ?.invoke() as? T
                        ?: throw NoMethodStubException()
            }
        }
    }
}