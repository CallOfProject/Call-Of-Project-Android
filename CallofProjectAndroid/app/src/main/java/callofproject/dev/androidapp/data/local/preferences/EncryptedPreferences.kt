package callofproject.dev.androidapp.data.local.preferences

import android.content.SharedPreferences
import callofproject.dev.androidapp.domain.preferences.IEncryptedPreferences
import callofproject.dev.androidapp.domain.preferences.IEncryptedPreferences.Companion.KEY_USER_PASSWORD
import javax.inject.Inject

class EncryptedPreferences @Inject constructor(
    private val encryptedPreferences: SharedPreferences
) : IEncryptedPreferences {

    override fun saveUserPassword(password: String) {
        encryptedPreferences.edit().putString(KEY_USER_PASSWORD, password).apply()
    }

    override fun getUserPassword(): String? {
        return encryptedPreferences.getString(KEY_USER_PASSWORD, null)
    }
}