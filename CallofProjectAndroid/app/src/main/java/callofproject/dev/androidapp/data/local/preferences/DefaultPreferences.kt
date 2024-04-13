package callofproject.dev.androidapp.data.local.preferences

import android.content.SharedPreferences
import callofproject.dev.androidapp.domain.preferences.IPreferences

class DefaultPreferences(private val preferences: SharedPreferences) : IPreferences {
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

}