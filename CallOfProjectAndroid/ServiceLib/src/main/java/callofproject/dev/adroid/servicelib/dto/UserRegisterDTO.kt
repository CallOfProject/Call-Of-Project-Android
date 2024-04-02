package callofproject.dev.adroid.servicelib.dto

data class UserRegisterDTO(
    var first_name: String = "",
    var last_name: String = "",
    var middle_name: String? = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var birth_date: String = ""
) {
    override fun toString(): String {
        return "UserRegisterDTO(first_name='$first_name', last_name='$last_name', middle_name=$middle_name, username='$username', email='$email', password='$password', birth_date='$birth_date')"
    }
}
