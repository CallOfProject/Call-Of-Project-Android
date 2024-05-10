package callofproject.dev.androidapp.di

import callofproject.dev.androidapp.presentation.user_profile.UserProfileViewModelHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelHelper {

    @Provides
    @ViewModelScoped
    fun provideViewModelHelper(): UserProfileViewModelHelper = UserProfileViewModelHelper()
}