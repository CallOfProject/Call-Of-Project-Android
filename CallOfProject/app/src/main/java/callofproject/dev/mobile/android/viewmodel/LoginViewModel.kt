package callofproject.dev.mobile.android.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // Kullanıcı adı ve şifre state'leri
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // Giriş yapma işlemini simüle etmek için bir fonksiyon
    fun login() {
        viewModelScope.launch {
            // Giriş yapma işlemini burada simüle edebilirsiniz.
            // Örneğin, bir API çağrısı yapabilir ve sonucuna göre bir aksiyon gerçekleştirebilirsiniz.
        }
    }
}
