package com.example.podcastexercise.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LifecycleOwner.observe(data: LiveData<T>, onChange: (T?) -> Unit) {
    data.observe(this, Observer<T?> {
        onChange(it)
    })
}

fun <T> LifecycleOwner.observeNonNull(data: LiveData<T>, onChange: (T) -> Unit) {
    data.observe(this, Observer<T> {
        if (it != null) {
            onChange(it)
        }
    })
}

fun LifecycleOwner.observeVisible(data: LiveData<Boolean>, view: View) {
    observe(data) {
        view.visibility = if (it == true) View.VISIBLE else View.GONE
    }
}

fun LifecycleOwner.observeDisable(data: LiveData<Boolean>, views: List<View>) {
    observe(data) {
        views.forEach { view ->
            view.isEnabled = it != true
        }
    }
}