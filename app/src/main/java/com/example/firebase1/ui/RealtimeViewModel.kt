package com.example.firebase1.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase1.firebaseRealtimeDb.RealtimeModelResponse
import com.example.firebase1.firebaseRealtimeDb.repository.RealtimeRepository
import com.example.firebase1.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repo: RealtimeRepository
) : ViewModel(){

    private val _res : MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    fun insert(items: RealtimeModelResponse.RealtimeItems) = repo.insert(items)

    private val _updateRes: MutableState<RealtimeModelResponse> = mutableStateOf(
        RealtimeModelResponse(
            items = RealtimeModelResponse.RealtimeItems(),
        )
    )
    val updateRes: State<RealtimeModelResponse> = _updateRes

    fun setData(data: RealtimeModelResponse){
        _updateRes.value = data
    }

    init {
        viewModelScope.launch {
            repo.getItems().collect{
                when(it){
                    is ResultState.Success -> {
                        _res.value = ItemState(
                            item = it.data
                        )
                    }
                    is ResultState.Failure->{
                        _res.value = ItemState(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading->{
                        _res.value = ItemState(
                            loading = true
                        )
                    }
                }
            }
        }
    }
    fun delete(key:String) = repo.delete(key)
    fun update(item : RealtimeModelResponse) = repo.update(item)
}
data class ItemState(
    val item : List<RealtimeModelResponse> = emptyList(),
    val error: String = "",
    val loading: Boolean = false
)