package callofproject.dev.androidapp.domain.dto;

import java.util.UUID

data class ProjectTag(val id: String, val tagName: String, val projectId: UUID) {
}