package callofproject.dev.androidapp.domain.preferences

interface IEncryptedPreferences {

    fun saveUserPassword(password: String)
    fun getUserPassword(): String?


    companion object {
        const val KEY_USER_PASSWORD = "USER_PASSWORD"
    }
}