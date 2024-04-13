package callofproject.dev.androidapp.di

import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object CallOfProjectModule {

    @Provides
    @ViewModelScoped
    fun provideCallOfProjectService(retrofit: Retrofit): ICallOfProjectService {
        return retrofit.create(ICallOfProjectService::class.java)
    }
}