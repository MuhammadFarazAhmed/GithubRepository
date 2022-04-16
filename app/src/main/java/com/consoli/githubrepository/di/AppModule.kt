package com.consoli.githubrepository.di


import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import com.app.base.utils.Constants
import com.app.interfaces.base.ParseErrors
import com.app.repositories.local.GithubRepositoryDB
import com.app.repositories.remote.client.*
import com.app.repositories.utils.PreferencesHelper
import com.consoli.githubrepository.BuildConfig
import com.consoli.githubrepository.di.qualifier.Base
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.joda.time.LocalDate
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module @InstallIn(SingletonComponent::class) class AppModule {
    
    @Provides @Base @Singleton internal fun retrofit(gson: Gson,
                                                     @Base client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonCustomConverterFactory.create(gson)).client(client).build()
    }
    
    @Provides @Base @Singleton internal fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                                                         @Base keyAuth: ApiKeyAuth,
                                                         sessionAuth: SessionAuth): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(keyAuth).addInterceptor(sessionAuth)
                .addInterceptor(loggingInterceptor).build()
    }
    
    @Provides @Singleton
    internal fun sessionAuth(preferencesHelper: PreferencesHelper): SessionAuth {
        return SessionAuth(preferencesHelper)
    }
    
    @Provides @Singleton internal fun gson(): Gson {
        return GsonBuilder().registerTypeAdapter(DateTime::class.java, DateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
                .setDateFormat(Constants.DATE_FORMAT_PATTERN)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting().create()
    }
    
    @Provides @Singleton internal fun parseErrors(): ParseErrors {
        return ParseErrors()
    }
    
    @Provides @Base @Singleton internal fun apiKeyAuth(): ApiKeyAuth {
        val apiKeyAuth = ApiKeyAuth("header", "api_key")
       // apiKeyAuth.apiKey = BuildConfig.API_KEY
        return apiKeyAuth
    }
    
    @Provides @Singleton internal fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
    
    @Provides @Singleton
    internal fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
    }
    
    @Provides @Singleton internal fun preferencesHelper(preferences: SharedPreferences,
                                                        gson: Gson): PreferencesHelper {
        return PreferencesHelper(preferences, gson)
    }
    
    @Provides @Singleton
    internal fun workManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
    
    @Provides @Singleton
    internal fun githubRepositoryDb(@ApplicationContext context: Context): GithubRepositoryDB {
        return Room.databaseBuilder(context, GithubRepositoryDB::class.java, "github_repo_db")
                .build()
    }
    
}
