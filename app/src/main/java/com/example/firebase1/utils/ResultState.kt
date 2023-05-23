package com.example.firebase1.utils

import com.example.firebase1.firebaseRealtimeDb.RealtimeModelResponse

sealed class ResultState<out T>{

    data class Success<out R>(val data: R) : ResultState<R>()
    data class Failure(val msg: Throwable) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}
