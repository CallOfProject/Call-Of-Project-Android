package callofproject.dev.androidapp.domain.preferences

import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import java.time.LocalDate

interface IPreferences {

    fun saveToken(token: String)
    fun getToken(): String?
    fun saveUsername(username: String)
    fun getUsername(): String?
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun saveFilterObjects(filterObjects: ProjectFilterDTO)
    fun getFilterObjects(): ProjectFilterDTO
    fun clearFilterObjects()
    fun clearUserInformation()
    fun saveLoginDate(now: LocalDate)

    fun getLoginDate(): LocalDate

    companion object {
        const val KEY_TOKEN = "TOKEN"
        const val KEY_USER_ID = "USER_ID"
        const val KEY_USERNAME = "USERNAME"
        const val KEY_FILTER_OBJECTS_START_DATE = "FILTER_OBJECTS_START_DATE"
        const val KEY_FILTER_OBJECTS_EXPECTED_COMPLETION_DATE =
            "FILTER_OBJECTS_EXPECTED_COMPLETION_DATE"
        const val KEY_FILTER_OBJECTS_APPLICATION_DEADLINE = "FILTER_OBJECTS_APPLICATION_DEADLINE"
        const val KEY_FILTER_OBJECTS_PROFESSION_LEVEL = "FILTER_OBJECTS_PROFESSION_LEVEL"
        const val KEY_FILTER_OBJECTS_PROJECT_LEVEL = "FILTER_OBJECTS_PROJECT_LEVEL"
        const val KEY_FILTER_OBJECTS_DEGREE = "FILTER_OBJECTS_DEGREE"
        const val KEY_FILTER_OBJECTS_FEEDBACK_TIME_RANGE = "FILTER_OBJECTS_FEEDBACK_TIME_RANGE"
        const val KEY_FILTER_OBJECTS_INTERVIEW_TYPE = "FILTER_OBJECTS_INTERVIEW_TYPE"
        const val KEY_FILTER_OBJECTS_PROJECT_STATUS = "FILTER_OBJECTS_PROJECT_STATUS"
        const val KEY_FILTER_OBJECTS_KEYWORD = "FILTER_OBJECTS_KEYWORD"
        const val KEY_LOGIN_DATE = "LOGIN_DATE"
    }
}