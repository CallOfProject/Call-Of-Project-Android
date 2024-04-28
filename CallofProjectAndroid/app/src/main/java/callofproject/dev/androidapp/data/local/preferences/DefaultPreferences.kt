package callofproject.dev.androidapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.preferences.IPreferences.Companion.KEY_FILTER_OBJECTS_APPLICATION_DEADLINE
import callofproject.dev.androidapp.domain.preferences.IPreferences.Companion.KEY_FILTER_OBJECTS_EXPECTED_COMPLETION_DATE
import callofproject.dev.androidapp.domain.preferences.IPreferences.Companion.KEY_FILTER_OBJECTS_FEEDBACK_TIME_RANGE
import callofproject.dev.androidapp.domain.preferences.IPreferences.Companion.KEY_FILTER_OBJECTS_PROFESSION_LEVEL
import callofproject.dev.androidapp.domain.preferences.IPreferences.Companion.KEY_LOGIN_DATE
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter.ofPattern
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val context: Context,
    private val preferences: SharedPreferences
) : IPreferences {
    override fun saveToken(token: String) {
        preferences.edit()
            .putString(IPreferences.KEY_TOKEN, "Bearer $token")
            .apply()
    }

    override fun getToken(): String? {
        return preferences.getString(IPreferences.KEY_TOKEN, null)
    }

    override fun saveUsername(username: String) {
        preferences.edit()
            .putString(IPreferences.KEY_USERNAME, username)
            .apply()
    }

    override fun getUsername(): String? {
        return preferences.getString(IPreferences.KEY_USERNAME, null)
    }

    override fun saveUserId(userId: String) {
        preferences.edit()
            .putString(IPreferences.KEY_USER_ID, userId)
            .apply()
    }

    override fun getUserId(): String? {
        return preferences.getString(IPreferences.KEY_USER_ID, null)
    }

    override fun saveFilterObjects(filterObjects: ProjectFilterDTO) {
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_START_DATE, filterObjects.startDate)
            .apply()
        preferences.edit()
            .putString(KEY_FILTER_OBJECTS_EXPECTED_COMPLETION_DATE, filterObjects.expectedCompletionDate)
            .apply()
        preferences.edit()
            .putString(KEY_FILTER_OBJECTS_APPLICATION_DEADLINE, filterObjects.applicationDeadline)
            .apply()
        preferences.edit()
            .putString(KEY_FILTER_OBJECTS_PROFESSION_LEVEL, filterObjects.professionLevel)
            .apply()
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_PROJECT_LEVEL, filterObjects.projectLevel)
            .apply()
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_DEGREE, filterObjects.degree)
            .apply()
        preferences.edit()
            .putString(KEY_FILTER_OBJECTS_FEEDBACK_TIME_RANGE, filterObjects.feedbackTimeRange)
            .apply()
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_INTERVIEW_TYPE, filterObjects.interviewType)
            .apply()
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_PROJECT_STATUS, filterObjects.projectStatus)
            .apply()
        preferences.edit()
            .putString(IPreferences.KEY_FILTER_OBJECTS_KEYWORD, filterObjects.keyword)
            .apply()
    }

    override fun getFilterObjects(): ProjectFilterDTO = ProjectFilterDTO(
        professionLevel = getProfessionLevel(),
        projectLevel = getProjectLevel(),
        degree = getDegree(),
        feedbackTimeRange = getFeedbackTimeRange(),
        interviewType = getInterviewType(),
        projectStatus = getProjectStatus(),
        startDate = getStartDate(),
        expectedCompletionDate = getExpectedCompletionDate(),
        applicationDeadline = getApplicationDeadline(),
        keyword = getKeyword()
    )

    override fun clearFilterObjects() = preferences.edit()
        .remove(IPreferences.KEY_FILTER_OBJECTS_START_DATE)
        .remove(KEY_FILTER_OBJECTS_EXPECTED_COMPLETION_DATE)
        .remove(KEY_FILTER_OBJECTS_APPLICATION_DEADLINE)
        .remove(KEY_FILTER_OBJECTS_PROFESSION_LEVEL)
        .remove(IPreferences.KEY_FILTER_OBJECTS_PROJECT_LEVEL)
        .remove(IPreferences.KEY_FILTER_OBJECTS_DEGREE)
        .remove(KEY_FILTER_OBJECTS_FEEDBACK_TIME_RANGE)
        .remove(IPreferences.KEY_FILTER_OBJECTS_INTERVIEW_TYPE)
        .remove(IPreferences.KEY_FILTER_OBJECTS_PROJECT_STATUS)
        .remove(IPreferences.KEY_FILTER_OBJECTS_KEYWORD)
        .apply()

    override fun clearUserInformation() {
        clearFilterObjects()
        preferences.edit()
            .remove(IPreferences.KEY_TOKEN)
            .remove(IPreferences.KEY_USER_ID)
            .remove(IPreferences.KEY_USERNAME)
            .remove(KEY_LOGIN_DATE)
            .apply()
    }

    override fun saveLoginDate(now: LocalDate) {
        preferences.edit()
            .putString(KEY_LOGIN_DATE, ofPattern(context.getString(R.string.local_date_formatter)).format(now))
            .apply()
    }

    override fun getLoginDate(): LocalDate {
        val now = ofPattern(context.getString(R.string.local_date_formatter)).format(now())

        val loginDate = preferences.getString(KEY_LOGIN_DATE, now)

        return LocalDate.parse(
            loginDate,
            ofPattern(context.getString(R.string.local_date_formatter))
        )
    }

    private fun getStartDate(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_START_DATE, "")
    }

    private fun getExpectedCompletionDate(): String? {
        return preferences.getString(KEY_FILTER_OBJECTS_EXPECTED_COMPLETION_DATE, "")
    }

    private fun getApplicationDeadline(): String? {
        return preferences.getString(KEY_FILTER_OBJECTS_APPLICATION_DEADLINE, "")
    }

    private fun getProfessionLevel(): String? {
        return preferences.getString(KEY_FILTER_OBJECTS_PROFESSION_LEVEL, "")
    }

    private fun getProjectLevel(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_PROJECT_LEVEL, "")
    }

    private fun getDegree(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_DEGREE, "")
    }

    private fun getFeedbackTimeRange(): String? {
        return preferences.getString(KEY_FILTER_OBJECTS_FEEDBACK_TIME_RANGE, "")
    }

    private fun getInterviewType(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_INTERVIEW_TYPE, "")
    }

    private fun getProjectStatus(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_PROJECT_STATUS, "")
    }

    private fun getKeyword(): String? {
        return preferences.getString(IPreferences.KEY_FILTER_OBJECTS_KEYWORD, "")
    }
}