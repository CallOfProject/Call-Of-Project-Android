package callofproject.dev.androidapp.domain.preferences

interface IPreferences {

    fun saveToken(token: String)
    fun getToken(): String?

    fun saveUsername(username: String)
    fun getUsername(): String?

    fun saveUserId(userId: String)
    fun getUserId(): String?

    companion object {
        const val KEY_TOKEN = "TOKEN"
        const val KEY_USER_ID = "USER_ID"
        const val KEY_USERNAME = "USERNAME"
    }
}