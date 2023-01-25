package com.bill.videosaver.base

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VM : AppViewModel>(layoutRes: Int) : Fragment(layoutRes) {

    open val snackbarAttachView: View
        get() = requireActivity().findViewById(android.R.id.content)

    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents(viewModel.events, ::onEvent)
    }

    @CallSuper
    protected open fun onEvent(event: Event) {
        when (event) {
            is ShowSnackbarEvent -> {
                val message = event.stringId?.let(::getString) ?: event.message!!
                showSnackbar(
                    message = message,
                    actionButton = event.snackbarButton,
                    duration = event.duration
                )
            }
        }
    }

    private fun showSnackbar(
        message: CharSequence,
        actionButton: SnackbarButton?,
        duration: Int
    ) {
        Snackbar.make(snackbarAttachView, message, duration).apply {
            if (actionButton != null) {
                setAction(actionButton.actionButtonResId) { actionButton.onActionClicked(this) }
            }
        }
            .show()
    }

    inline fun <T> LiveData<T>.observe(crossinline block: (T) -> Unit){
        observe(liveData = this, block = block)
    }
}
