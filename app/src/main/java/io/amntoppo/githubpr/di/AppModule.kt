package io.amntoppo.githubpr.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.amntoppo.githubpr.BuildConfig
import io.amntoppo.githubpr.data.local.Converters
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.utils.GsonParser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getHttpClient(): OkHttpClient {
        val interceptor: Interceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.AUTH_TOKEN}")
            requestBuilder.addHeader("User-Agent", "Github-Closed-PR-App")
            chain.proceed(requestBuilder.build())
        }
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRepositoryApi(retrofit: Retrofit): RepositoryApi =
        retrofit.create(RepositoryApi::class.java)

    @Provides
    @Singleton
    fun providePullRequestApi(retrofit: Retrofit): PullRequestApi =
        retrofit.create(PullRequestApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): GithubDatabase =
        Room.databaseBuilder(app, GithubDatabase::class.java, "github_database")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
}