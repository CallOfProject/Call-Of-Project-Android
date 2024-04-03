package callofproject.dev.core.use_case

class TextValidate {
    operator fun invoke(text: String): String = text.trim()
}