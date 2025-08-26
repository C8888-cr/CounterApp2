package com.example.counterapp2

import CounterViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import counterapp2.composeapp.generated.resources.Res
import counterapp2.composeapp.generated.resources.compose_multiplatform
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun App(viewModel: CounterViewModel = viewModel()) {
    MaterialTheme {
        // State vom ViewModel beobachten. Compose reagiert automatisch auf Änderungen.
        val uiState by viewModel.uiState.collectAsState()

        CounterScreen(
            state = uiState,
            onIncrement = { viewModel.increment() },
            onDecrement = { viewModel.decrement() },
            onIncrementAsync = { viewModel.incrementAsync() }
        )
    }
}

@Composable
fun CounterScreen(
    state: CounterState,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onIncrementAsync: () -> Unit
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Count: ${state.count}")
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        Row {
            Button(onClick = onIncrement, enabled = !state.isLoading) { Text("+") }
            Button(onClick = onDecrement, enabled = !state.isLoading) { Text("-") }
        }
        Button(onClick = onIncrementAsync, enabled = !state.isLoading) { Text("Increment Async") }
    }
}