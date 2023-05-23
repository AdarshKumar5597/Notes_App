package com.example.firebase1.firebaseRealtimeDb.repository

import com.example.firebase1.firebaseRealtimeDb.RealtimeModelResponse
import com.example.firebase1.utils.ResultState
import kotlinx.coroutines.flow.Flow


interface RealtimeRepository {
    fun insert (items: RealtimeModelResponse.RealtimeItems) : Flow<ResultState<String>>
    fun getItems() : Flow<ResultState<List<RealtimeModelResponse>>>
    fun delete(key: String) : Flow<ResultState<String>>
    fun update(res: RealtimeModelResponse): Flow<ResultState<String>>
}