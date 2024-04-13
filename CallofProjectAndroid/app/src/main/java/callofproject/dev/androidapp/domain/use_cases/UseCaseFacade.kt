package callofproject.dev.androidapp.domain.use_cases

data class UseCaseFacade (
    val loginUseCase: LoginUseCase,
    val registerUseCase: RegisterUseCase,
    val projectDiscoveryUseCase: ProjectDiscoveryUseCase,
    val projectDetailsUseCase: ProjectDetailsUseCase,
    val findUserProfileUseCase: FindUserProfileUseCase,
    val upsertEducationUseCase: UpsertEducationUseCase
)