package com.example.firebase1.firebaseRealtimeDb.repository
import com.example.firebase1.firebaseRealtimeDb.RealtimeModelResponse
import com.example.firebase1.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDbRepository @Inject constructor(
    private val db: DatabaseReference
) : RealtimeRepository{
    override fun insert(items: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.push().setValue(items)
            .addOnSuccessListener {
                trySend(ResultState.Success("Data inserted successfully!"))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose{
            close()
        }
    }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> = callbackFlow {
        trySend(ResultState.Loading)
        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealtimeModelResponse(
                        it.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }
        db.addValueEventListener(valueEvent)
        awaitClose{
            db.removeEventListener(valueEvent)
            close()
        }
    }


    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.child(key).removeValue()
            .addOnCompleteListener{
                trySend(ResultState.Success("Item deleted!"))
            }
            .addOnFailureListener{
                trySend(ResultState.Failure(it))
            }
        awaitClose{
            close()
        }
    }

    override fun update(res: RealtimeModelResponse): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val map = HashMap<String, Any>()
        map["title"] = res.items?.title!!
        map["description"] = res.items.description!!
        db.child(res.key!!).updateChildren(map)
            .addOnCompleteListener{
                trySend(ResultState.Success("Value updated successfully!"))
            }
            .addOnFailureListener{
            trySend(ResultState.Failure(it))
        }
        awaitClose{
            close()
        }
    }
}