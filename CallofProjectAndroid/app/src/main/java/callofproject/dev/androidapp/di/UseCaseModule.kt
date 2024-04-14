package callofproject.dev.androidapp.di

import android.content.Context
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.FindUserProfileUseCase
import callofproject.dev.androidapp.domain.use_cases.LoginUseCase
import callofproject.dev.androidapp.domain.use_cases.ProjectDetailsUseCase
import callofproject.dev.androidapp.domain.use_cases.ProjectDiscoveryUseCase
import callofproject.dev.androidapp.domain.use_cases.RegisterUseCase
import callofproject.dev.androidapp.domain.use_cases.SaveCourseUseCase
import callofproject.dev.androidapp.domain.use_cases.SaveEducationUseCase
import callofproject.dev.androidapp.domain.use_cases.SaveExperienceUseCase
import callofproject.dev.androidapp.domain.use_cases.SaveLinkUseCase
import callofproject.dev.androidapp.domain.use_cases.UpdateCourseUseCase
import callofproject.dev.androidapp.domain.use_cases.UpdateEducationUseCase
import callofproject.dev.androidapp.domain.use_cases.UpdateExperienceUseCase
import callofproject.dev.androidapp.domain.use_cases.UpdateLinkUseCase
import callofproject.dev.androidapp.domain.use_cases.UpdateUserProfileUseCase
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideUseCaseFacade(
        service: ICallOfProjectService,
        @ApplicationContext context: Context,
        preferences: IPreferences
    ): UseCaseFacade {
        return UseCaseFacade(
            login = LoginUseCase(service, context),
            register = RegisterUseCase(service),
            projectDiscovery = ProjectDiscoveryUseCase(service, preferences = preferences),
            projectDetails = ProjectDetailsUseCase(service, preferences = preferences),
            findUserProfile = FindUserProfileUseCase(service, preferences = preferences),
            saveEducation = SaveEducationUseCase(service, preferences = preferences),
            updateEducation = UpdateEducationUseCase(service, preferences = preferences),
            saveExperience = SaveExperienceUseCase(service, preferences = preferences),
            updateExperience = UpdateExperienceUseCase(service, preferences = preferences),
            saveCourse = SaveCourseUseCase(service, preferences = preferences),
            updateCourse = UpdateCourseUseCase(service, preferences = preferences),
            saveLink = SaveLinkUseCase(service, preferences = preferences),
            updateLink = UpdateLinkUseCase(service, preferences = preferences),
            updateProfile = UpdateUserProfileUseCase(service, preferences = preferences)
        )
    }
}