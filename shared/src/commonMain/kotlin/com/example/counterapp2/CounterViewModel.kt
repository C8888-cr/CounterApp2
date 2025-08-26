import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.stateIn

open class CounterViewModel: ViewModel() {
    // Erzeuge einen CoroutineScope, der an die Lebenszeit des ViewModels gebunden ist
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    // Privater, veränderlicher StateFlow für interne Updates
    private val _uiState = MutableStateFlow(CounterState())
    // Öffentlicher, nur lesbarer StateFlow für die UI
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

    // Wichtig, um Speicherlecks zu vermeiden, wenn das VM nicht mehr gebraucht wird
    fun clear() {
        viewModelScope.cancel()
    }
}