package callofproject.dev.adroid.servicelib.dto

data class UserRegisterDTO(
    val first_name: String,
    val last_name: String,
    val middle_name: String? = "",
    val username: String,
    val email: String,
    val password: String,
    val birth_date: String)
{
    override fun toString(): String {
        return "UserRegisterDTO(first_name='$first_name', last_name='$last_name', middle_name=$middle_name, username='$username', email='$email', password='$password', birth_date='$birth_date')"
    }
}
