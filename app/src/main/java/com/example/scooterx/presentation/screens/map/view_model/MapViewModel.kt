package com.example.scooterx.presentation.screens.map.view_model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scooterx.core.OnError
import com.example.scooterx.core.OnSuccess
import com.example.scooterx.core.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class MapRepo {
    private val fireStore = FirebaseFirestore.getInstance()

    fun getModelDetails() = callbackFlow {
        val collection = fireStore.collection("scooters")
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null){
                OnSuccess(value)
            } else {
                OnError(error)
            }
            this.trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}

class MapViewModel(private val mapRepo: MapRepo) : ViewModel() {

    val modelsStateFlow = MutableStateFlow<Response?>(null)
    init {
        viewModelScope.launch {
            mapRepo.getModelDetails().collect {
                modelsStateFlow.value = it
            }
        }
    }
}

class ViewModelFactory(private val mapRepo: MapRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(mapRepo) as T
        }
        throw IllegalStateException()
    }
}