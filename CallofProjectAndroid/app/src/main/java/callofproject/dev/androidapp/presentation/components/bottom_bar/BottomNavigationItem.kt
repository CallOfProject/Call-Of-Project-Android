package callofproject.dev.androidapp.presentation.components.bottom_bar

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val hasNews: Boolean,
    var badgeCount: Int? = 10
)