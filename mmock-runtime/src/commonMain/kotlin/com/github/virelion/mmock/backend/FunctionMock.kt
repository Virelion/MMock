package com.github.virelion.mmock.backend

class FunctionMock<R>(
        val verificationFunction: ArgumentsConstraints,
        val body: ((Array<out Any?>) -> R)
) {
    operator fun invoke(vararg args: Any?): R {
        return body.invoke(args)
    }
}