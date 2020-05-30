package com.github.virelion.mmock.dsl

import com.github.virelion.mmock.MMockVerificationException
import com.github.virelion.mmock.backend.RecordLog
import com.github.virelion.mmock.backend.stack.Invocation
import com.github.virelion.mmock.backend.stack.InvocationCountRuleElement

@MMockDSL
class VerificationContext(
    private val recordLog: RecordLog,
    private val recordingContext: RecordingContext
) : RecordingContext by recordingContext, InvocationProtection {
    @MMockDSL
    infix fun InvocationProtection.InvocationVerification.called(rule: InvocationMatcher) {
        recordingStack?.add(InvocationCountRuleElement(rule))
    }

    @MMockDSL
    suspend fun sequence(block: suspend SequenceVerificationContext.() -> Unit) {
        SequenceVerificationContext(recordLog, recordingContext).verify(block)
    }

    @MMockDSL
    suspend fun order(block: suspend OrderVerificationContext.() -> Unit) {
        OrderVerificationContext(recordLog, recordingContext).verify(block)
    }

    internal suspend fun verify(block: suspend VerificationContext.() -> Unit) {
        val invocations = record { block() }
        verifyInvocations(invocations)
    }

    private fun verifyInvocations(invocations: List<Invocation<*>>) {
        invocations.forEach { invocation ->
            val invocationAmount = recordLog.log.count {
                it.fits(invocation)
            }
            val rule = invocation.invocationConstraint ?: { it == 1 }
            if (!rule(invocationAmount)) throw MMockVerificationException("Function ${invocation.name} invoked $invocationAmount times")
        }
    }
}
