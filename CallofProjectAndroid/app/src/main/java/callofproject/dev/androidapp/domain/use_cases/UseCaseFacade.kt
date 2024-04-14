package callofproject.dev.androidapp.domain.use_cases

data class UseCaseFacade(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val projectDiscovery: ProjectDiscoveryUseCase,
    val projectDetails: ProjectDetailsUseCase,
    val findUserProfile: FindUserProfileUseCase,
    val saveEducation: SaveEducationUseCase,
    val updateEducation: UpdateEducationUseCase,
    val saveExperience: SaveExperienceUseCase,
    val updateExperience: UpdateExperienceUseCase,
    val saveCourse: SaveCourseUseCase,
    val updateCourse: UpdateCourseUseCase,
    val saveLink: SaveLinkUseCase,
    val updateLink: UpdateLinkUseCase,
    val updateProfile: UpdateUserProfileUseCase
)