package callofproject.dev.core.data.preferences

import android.content.SharedPreferences
import callofproject.dev.core.domain.preferences.IPreferences

class DefaultSharedPreferences(val sharedPreferences: SharedPreferences) : IPreferences {
}