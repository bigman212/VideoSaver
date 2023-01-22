package com.bill.videosaver.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import android.app.Activity
import java.util.LinkedList
import java.util.Queue

interface Event

class EventsQueue : MutableLiveData<Queue<Event>>() {
    fun push(event: Event) {
        val currentValueOrNew = value ?: LinkedList()
        currentValueOrNew.add(event)
        value = currentValueOrNew
    }
}

fun Fragment.observeEvents(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    observe(eventsQueue) { queue: Queue<Event> ->
        while (queue.isNotEmpty()) {
            eventHandler(queue.remove())
        }
    }
}

fun Activity.observeEvents(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    eventsQueue.observe(this as LifecycleOwner) { queue: Queue<Event> ->
        while (queue.isNotEmpty()) {
            eventHandler(queue.remove())
        }
    }
}
