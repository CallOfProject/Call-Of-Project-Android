package callofproject.dev.androidapp.data.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun toLocalDate(date: String, formatter: DateTimeFormatter): LocalDate {
    return LocalDate.parse(date, formatter)
}