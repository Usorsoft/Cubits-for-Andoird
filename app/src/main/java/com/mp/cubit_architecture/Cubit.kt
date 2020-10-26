package com.example.cubit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import com.mp.cubit_architecture.foundation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * Serves as the business logic component for the [MainActivty].
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright EnBW AG
 */
abstract class Cubit<STATE : Any> {
    private val actionListeners = mutableListOf<ActionCallbackPair<Any>>()
    private val jobs = mutableMapOf<String, Job>()
    protected var state: STATE private set
    val mutableState: MutableState<STATE>

    init {
        state = initState()
        mutableState = mutableStateOf(state)
    }

    protected abstract fun initState(): STATE
    protected abstract fun onDispose()

    /**
     * This registers a callback which provides an action each time the [Cubit] emits a new action
     * via [emitAction]. [callIf] is used internally to decide if the [ActionCallback] should be
     * invoked for a emitted action or not.
     */
    fun <T : Any> addActionListener(changeListener: StateChangeListener<T>, callIf: ActionMatcher) {
        ActionCallbackPair(
            callIf,
            changeListener as StateChangeListener<Any>
        ).let(actionListeners::add)
    }

    /**
     * This removes the registered action [changeListener].
     */
    fun <T : Any> removeActionListener(changeListener: StateChangeListener<T>) {
        actionListeners.removeAll { it.callback == changeListener }
    }

    /**
     * This emits an state to the [CubitActivity] which can be observed via [CubitActivity.onStateChange].
     */
    @CubitDsl
    protected fun emitState(state: STATE) {
        if (this.state == state) {
            return
        }
        this.state = state
        runOnMain { mutableState.value = state }
    }

    /**
     * Emits the current state.
     */
    fun emitCurrentState() {
        runOnMain { mutableState.value = state }
    }

    /**
     * This emits an action to the [CubitActivity] which can be observes via [addActionListener].
     * The [action] can by any type of object.
     */
    @CubitDsl
    protected fun <T : Any> emitAction(action: T) {
        runOnMain {
            val actionCallbackPair = actionListeners.find { it.matcher(action) }
            actionCallbackPair?.callback?.invoke(action)
        }
    }

    /**
     * This creates a suspendable coroutine scope. It allows for doing asynchronous operations
     * is a sequential manner. Provide an [jobId] if you want the job to be cancelled if the same
     *  coroutine scope will be re-invoking.
     */
    @CubitDsl
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    protected fun async(
        jobId: String? = null,
        cancelExistingJob: Boolean = false,
        suspendable: suspend CoroutineScope.() -> Unit
    ) {
        val (job, didCreateNew) = getJob(jobId, cancelExistingJob)
        if (didCreateNew) {
            CoroutineScope(job).launch {
                suspendable(this)
                if (jobId != null && !cancelExistingJob) {
                    jobs[jobId]?.cancel()
                    jobs.remove(jobId)
                }
            }
        }
    }

    @CubitDsl
    protected fun cancelJob(jobId: String) {
        jobs[jobId]?.cancel()
        jobs.remove(jobId)
    }

    /**
     * This creates a new [CoroutineScope]. If an [jobId] is provided, the [Job] for the
     * [CoroutineScope] will be registered internally or cancelled if a [Job] for the given [jobId]
     * is already registered.
     */
    private fun getJob(jobId: String? = null, cancelExistingJob: Boolean): Pair<Job, Boolean> {
        val id = jobId ?: return Pair(Job(), true)
        val existingJob = jobs[id]

        return when {
            cancelExistingJob -> {
                existingJob?.cancel()
                val newJob = Job()
                jobs[jobId] = newJob
                return Pair(newJob, true)
            }
            existingJob == null -> {
                val newJob = Job()
                jobs[id] = newJob
                return Pair(newJob, true)
            }
            existingJob.isCancelled -> {
                val newJob = Job()
                jobs[id] = newJob
                return Pair(newJob, true)
            }
            else -> {
                Pair(existingJob, false)
            }
        }
    }

    /**
     * This releases and clears all references to [Job]s, [StateChangeListener]s and [ActionCallback]s.
     */
    fun dispose() {
        onDispose()
        actionListeners.clear()
        jobs.forEach { it.value.cancel() }
        jobs.clear()
    }

    companion object
}