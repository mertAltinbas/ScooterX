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



class InstructionRepo{

    private val fireStore = FirebaseFirestore.getInstance()
    fun getInstructionDetails() = callbackFlow {
        val collection = fireStore.collection("instruction")
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

class InstructionViewModel(private val instructionRepo: InstructionRepo) : ViewModel() {

    val instructionStateFlow = MutableStateFlow<Response?>(null)
    init {
        viewModelScope.launch {
            instructionRepo.getInstructionDetails().collect {
               instructionStateFlow.value = it
            }
        }
    }
}
class InstructionViewModelFactory(private val instructionRepo: InstructionRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InstructionViewModel::class.java)) {
            return InstructionViewModel(instructionRepo) as T
        }
        throw IllegalStateException()
    }
}