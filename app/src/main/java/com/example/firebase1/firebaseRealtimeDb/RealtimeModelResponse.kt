package com.example.firebase1.firebaseRealtimeDb

data class RealtimeModelResponse(
    val items: RealtimeItems?,
    val key: String?
){
    data class RealtimeItems(
        val title: String?,
        val description: String?
    )
}
