package com.github.virelion.mmock.backend.unsafe

import java.lang.reflect.Proxy

actual inline fun <reified T> createUnsafe(): T {
    return if (T::class.java.isInterface) {
        Proxy.newProxyInstance(
                T::class.java.classLoader,
                arrayOf(T::class.java),
                ThrowingInvocationHandler()
        ) as T
    } else {
        JVMUnsafe.createUnsafeIntsanceOfType()
    }
}
