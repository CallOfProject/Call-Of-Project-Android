package callofproject.dev.androidapp.di

import android.content.Context
import callofproject.dev.androidapp.R
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {
    @Provides
    @ViewModelScoped
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder().baseUrl(context.getString(R.string.remote_baseUrl))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(OkHttpClient.Builder().build()).build()
    }
}