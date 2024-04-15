package callofproject.dev.androidapp.di

import android.content.Context
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.domain.use_cases.AuthenticationUseCase
import callofproject.dev.androidapp.domain.use_cases.ProjectUseCase
import callofproject.dev.androidapp.domain.use_cases.UploadFileUseCase
import callofproject.dev.androidapp.domain.use_cases.UseCaseFacade
import callofproject.dev.androidapp.domain.use_cases.UserProfileUseCase
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
            authentication = AuthenticationUseCase(service, context),
            uploadFile = UploadFileUseCase(service, preferences, context),
            project = ProjectUseCase(service, preferences),
            userProfile = UserProfileUseCase(service, preferences),
        )
    }
}