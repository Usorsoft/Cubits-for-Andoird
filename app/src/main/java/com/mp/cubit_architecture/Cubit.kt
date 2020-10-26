package com.example.cubit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.mp.cubit_architecture.foundation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * This represents the basic business logic component of the cubit architecture.
 * The idea behind Cubits is that  cubit is responsible for doing business by implementing custom
 * functions without return value! Instead the function call results in a new state which can be
 * exposed via [emitState]. This changes the [state] and [mutableState] property. To be notified
 * about state changes, start observing the [observableState] or use the [StateViewOf.StateBuilder]
 * to rebuild a [Composable] scope in Jetpack Compose.
 *
 * For suspendable operations please use the [async] method. This ensures that all coroutine [Job]s
 * are properly managed by the [Cubit].
 *
 * Please declare the type of the state the cubit is aware about via [STATE].
 *
 * There is also a [emitAction] method for exposing actions to a view or activity which do not have
 * impact to the state of a view, like navigation handling. In order to be able to observe emitted
 * actions, use [addActionListener] and [removeActionListener].
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
abstract class Cubit<STATE : Any> {
    private val actionListeners = mutableListOf<ActionBundle<Any>>()
    private val jobs = mutableMapOf<String, Job>()
    protected var state: STATE private set
    val mutableState: MutableState<STATE>
    val observableState: MutableLiveData<STATE>

    init {
        state = initState()
        mutableState = mutableStateOf(state)
        observableState = MutableLiveData(state)
    }

    protected abstract fun initState(): STATE
    protected abstract fun onDispose()

    /**
     * This registers a callback which provides an action each time the [Cubit] emits a new action
     * via [emitAction]. [condition] is used internally to decide if the [ChangeListener] should be
     * invoked for a emitted action or not.
     */
    fun <T : Any> addActionListener(changeListener: ChangeListener<T>, condition: Condition) {
        ActionBundle(condition, changeListener as ChangeListener<Any>).let(actionListeners::add)
    }

    /**
     * This removes the registered action [changeListener].
     */
    fun <T : Any> removeActionListener(changeListener: ChangeListener<T>) {
        actionListeners.removeAll { it.callback == changeListener }
    }

    /**
     * This emits an state which can be observed via [observableState] or [mutableState] using
     * [StateViewOf.StateBuilder] in a [Composable].
     */
    @CubitDsl
    protected fun emitState(state: STATE) {
        if (this.state == state) { return }
        this.state = state
        runOnMain {
            mutableState.value = state
            observableState.value = state
        }
    }

    /**
     * Emits the current state.
     */
    fun emitCurrentState() {
        runOnMain {
            mutableState.value = state
            observableState.value = state
        }
    }

    /**
     * This emits an action to the to all action listeners added via [addActionListener].
     * The [action] can be any type of object.
     */
    @CubitDsl
    protected fun <T : Any> emitAction(action: T) {
        runOnMain {
            val actionCallbackPair = actionListeners.find { it.condition(action) }
            actionCallbackPair?.callback?.invoke(action)
        }
    }

    /**
     * This creates a suspendable coroutine scope. It allows for doing asynchronous operations
     * is a sequential manner. Specify a [jobId] to prevent a job from being executed multiple
     * times at once. Specify a [jobId] and set [cancelExistingJob] true if you want the job to
     * be cancelled if the same coroutine scope will be re-invoked.
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

    /**
     * This cancels the [Job] connected to the given [jobId] if exist or running.
     */
    @CubitDsl
    protected fun cancelJob(jobId: String) {
        jobs[jobId]?.cancel()
        jobs.remove(jobId)
    }

    /**
     * This creates a new [CoroutineScope]. If an [jobId] is provided, the [Job] for the
     * [CoroutineScope] will be registered internally or ignored if a [Job] for the given [jobId]
     * is already registered. Use [cancelExistingJob] true if you want the existing [Job] to be
     * cancelled instead.
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
     * This releases and clears all references to [Job]s and [ChangeListener]s.
     */
    fun dispose() {
        onDispose()
        actionListeners.clear()
        jobs.forEach { it.value.cancel() }
        jobs.clear()
    }

    companion object
}