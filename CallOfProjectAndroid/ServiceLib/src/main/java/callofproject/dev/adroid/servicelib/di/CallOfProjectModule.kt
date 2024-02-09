package callofproject.dev.adroid.servicelib.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object CallOfProjectModule {

    @Provides
    fun provideCallOfProjectService(retrofit: Retrofit): ICallOfProjectService {
        return retrofit.create(ICallOfProjectService::class.java)
    }
}