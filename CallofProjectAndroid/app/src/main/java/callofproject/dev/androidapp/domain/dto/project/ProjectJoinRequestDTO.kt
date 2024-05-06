package callofproject.dev.androidapp.domain.dto.project

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ProjectJoinRequestDTO(
    @SerializedName("request_id")
    val requestId: UUID,

    @SerializedName("notification_id")
    val notificationId: String,

    @SerializedName("is_accepted")
    val isAccepted: Boolean
)
