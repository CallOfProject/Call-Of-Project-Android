package callofproject.dev.androidapp.di

import android.content.Context
import callofproject.dev.androidapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterModule {

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
}