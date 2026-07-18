package com.example.netflixclone.core.utils

import kotlin.coroutines.cancellation.CancellationException

fun Throwable.rethrowIfCancellation() {
    if (this is CancellationException) {
        throw this
    }
}