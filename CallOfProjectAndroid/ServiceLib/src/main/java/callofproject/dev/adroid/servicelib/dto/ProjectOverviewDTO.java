package callofproject.dev.adroid.servicelib.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record ProjectOverviewDTO(
        @SerializedName("project_id")
        String projectId,
        @SerializedName("project_image_path")
        String projectImagePath,
        @SerializedName("project_title")
        String projectTitle,
        @SerializedName("project_summary")
        String projectSummary,
        @SerializedName("project_aim")
        String projectAim,
        @SerializedName("application_deadline")
        String applicationDeadline,
        @SerializedName("expected_completion_date")
        String expectedCompletionDate,
        @SerializedName("start_date")
        String startDate,
        @SerializedName("max_participant")
        int maxParticipant,
        @SerializedName("techinical_requirements")
        String technicalRequirements,
        @SerializedName("special_requirements")
        String specialRequirements,
        @SerializedName("project_profession_level")
        String professionLevel,
        @SerializedName("project_degree")
        String degree,
        @SerializedName("project_level")
        String projectLevel,
        @SerializedName("interview_type")
        String interviewType,
        @SerializedName("project_owner_name")
        String projectOwnerName,
        @SerializedName("feedback_time_range")
        String feedbackTimeRange,
        @SerializedName("project_status")
        String projectStatus,
        @SerializedName("project_tags")
        List<ProjectTag> projectTags) {


        @Override
        public String toString() {
                return "ProjectOverviewDTO{" +
                        "projectId='" + projectId + '\'' +
                        ", projectImagePath='" + projectImagePath + '\'' +
                        ", projectTitle='" + projectTitle + '\'' +
                        ", projectSummary='" + projectSummary + '\'' +
                        ", projectAim='" + projectAim + '\'' +
                        ", applicationDeadline='" + applicationDeadline + '\'' +
                        ", expectedCompletionDate='" + expectedCompletionDate + '\'' +
                        ", startDate='" + startDate + '\'' +
                        ", maxParticipant=" + maxParticipant +
                        ", technicalRequirements='" + technicalRequirements + '\'' +
                        ", specialRequirements='" + specialRequirements + '\'' +
                        ", professionLevel='" + professionLevel + '\'' +
                        ", degree='" + degree + '\'' +
                        ", projectLevel='" + projectLevel + '\'' +
                        ", interviewType='" + interviewType + '\'' +
                        ", projectOwnerName='" + projectOwnerName + '\'' +
                        ", feedbackTimeRange='" + feedbackTimeRange + '\'' +
                        ", projectStatus='" + projectStatus + '\'' +
                        ", projectTags=" + projectTags +
                        '}';
        }
}