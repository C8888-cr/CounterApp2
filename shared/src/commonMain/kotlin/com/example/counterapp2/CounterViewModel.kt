import com.example.counterapp2.CounterState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel

import com.rickclephas.kmp.observableviewmodel.launch

import kotlinx.coroutines.flow.update
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModelScope

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.delay



import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



open class CounterViewModel: ViewModel() {
    // Erzeuge einen CoroutineScope, der an

    //
    // die Lebenszeit des ViewModels gebunden ist
    //override val viewModelScope = CoroutineScope(Dispatchers.Main)

    // Privater, veränderlicher StateFlow für interne Updates
    private val _uiState = MutableStateFlow<CounterState>(viewModelScope as ViewModelScope, CounterState())
    // Öffentlicher, nur lesbarer StateFlow für die UI

    //Add NativeCou....
    @NativeCoroutinesState
    val uiState: StateFlow<CounterState> = _uiState.asStateFlow()

    fun increment() {
        // update ist eine sichere Methode, um den State zu aktualisieren
        _uiState.update { currentState ->
            currentState.copy(count = currentState.count + 1)
        }
    }

    fun decrement() {
        _uiState.update { currentState ->
            currentState.copy(count = currentState.count - 1)
        }
    }

    // Beispiel für eine asynchrone Aktion
    fun incrementAsync() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // Simuliere Arbeit
            _uiState.update { it.copy(count = it.count + 1, isLoading = false) }
        }
    }
/*


    // Wichtig, um Speicherlecks zu vermeiden, wenn das VM nicht mehr gebraucht wird
    fun clear() {
        viewModelScope.cancel()
    }*/
}
