package callofproject.dev.androidapp.util.route

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}