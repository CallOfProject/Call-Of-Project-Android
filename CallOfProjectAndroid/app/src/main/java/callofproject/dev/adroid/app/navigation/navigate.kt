package callofproject.dev.adroid.app.navigation

import androidx.navigation.NavController
import callofproject.dev.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}