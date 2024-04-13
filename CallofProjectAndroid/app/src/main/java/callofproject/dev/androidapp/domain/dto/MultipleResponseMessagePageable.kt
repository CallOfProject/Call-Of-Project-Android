package callofproject.dev.androidapp.domain.dto

class MultipleResponseMessagePageable<T>(
    val totalPage: Long,
    val page: Int,
    val itemCount: Int,
    val message: String,
    val `object`: T
) {
    private val total_page: Long = 0
    private val item_count = 0

}