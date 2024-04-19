package callofproject.dev.androidapp.domain.use_cases

data class UseCaseFacade(
    val authentication: AuthenticationUseCase,
    val uploadFile: UploadFileUseCase,
    val project: ProjectUseCase,
    val userProfile: UserProfileUseCase,
    val notification: NotificationUseCase
)