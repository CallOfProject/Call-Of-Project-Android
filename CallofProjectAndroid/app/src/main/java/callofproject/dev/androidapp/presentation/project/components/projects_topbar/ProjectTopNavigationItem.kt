package callofproject.dev.androidapp.presentation.project.components.projects_topbar

import androidx.compose.ui.graphics.painter.Painter

data class ProjectTopNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
)