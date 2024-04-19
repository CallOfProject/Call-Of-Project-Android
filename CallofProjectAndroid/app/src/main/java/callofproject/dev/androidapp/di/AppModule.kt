package callofproject.dev.androidapp.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import callofproject.dev.androidapp.R
import callofproject.dev.androidapp.data.local.preferences.DefaultPreferences
import callofproject.dev.androidapp.domain.preferences.IPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences(context.getString(R.string.shared_pref_name), MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): IPreferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideDateTimeFormatter(@ApplicationContext context: Context): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(context.getString(R.string.local_datetime_formatter))
    }

    @Provides
    @Singleton
    fun provideDateTimeFormatterForLocalDate(@ApplicationContext context: Context): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(context.getString(R.string.local_date_formatter))
    }

    @Provides
    @Singleton
    fun provideLocalDateTime(): LocalDateTime = LocalDateTime.now()

    @Provides
    @Singleton
    fun provideLocalDate(): LocalDate = LocalDate.now()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}