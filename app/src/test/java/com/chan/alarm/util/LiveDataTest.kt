package com.chan.alarm.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.chan.ui.livedata.Event
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<Event<T>>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): Event<T> {
    var data: Event<T>? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<Event<T>> {
        override fun onChanged(o: Event<T>?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as Event<T>
}
