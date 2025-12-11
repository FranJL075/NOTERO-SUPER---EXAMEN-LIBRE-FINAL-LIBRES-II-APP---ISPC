package com.notero.superapp.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> StateFlow<T>.collectIn(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        this@collectIn.collect { block(it) }
    }
}
