package callofproject.dev.androidapp.di

import android.content.Context
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.FindUserProfileUseCase
import callofproject.dev.androidapp.domain.use_cases.LoginUseCase
import callofproject.dev.androidapp.domain.use_cases.ProjectDetailsUseCase
import callofproject.dev.androidapp.domain.use_cases.ProjectDiscoveryUseCase
import callofproject.dev.androidapp.domain.use_cases.RegisterUseCase
import callofproject.dev.androidapp.domain.use_cases.UpsertEducationUseCase
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
            loginUseCase = LoginUseCase(service, context),
            registerUseCase = RegisterUseCase(service),
            projectDiscoveryUseCase = ProjectDiscoveryUseCase(service, preferences = preferences),
            projectDetailsUseCase = ProjectDetailsUseCase(service, preferences = preferences),
            findUserProfileUseCase = FindUserProfileUseCase(service, preferences = preferences),
            upsertEducationUseCase = UpsertEducationUseCase(service, preferences = preferences),
        )
    }
}