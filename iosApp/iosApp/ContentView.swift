import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct ContentView: View {
    // SwiftUI kümmert sich um die Lebenszeit dieses Objekts
    @StateViewModel var viewModel = CounterViewModel()

    var body: some View {
        CounterScreen(
            state: viewModel.uiState,
            onIncrement: viewModel.increment,
            onDecrement: viewModel.decrement,
            onIncrementAsync: viewModel.incrementAsync
        )
    }
}

struct CounterScreen: View {
    var state: CounterState
    var onIncrement: () -> Void
    var onDecrement: () -> Void
    var onIncrementAsync: () -> Void

    var body: some View {
        VStack(spacing: 20) {
            Text("Count: \(state.count)")
            if state.isLoading {
                ProgressView()
            }
            HStack {
                Button("+", action: onIncrement).disabled(state.isLoading)
                Button("-", action: onDecrement).disabled(state.isLoading)
            }
            Button("Increment Async", action: onIncrementAsync).disabled(state.isLoading)
        }
    }
}
