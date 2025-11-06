package edu.ucne.nathiel_taveras_ap2_p2.Data.Module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.GastosApi
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.GastosRemoteDataSource
import edu.ucne.nathiel_taveras_ap2_p2.Data.Repository.GastosRepository
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Repository.GastosRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(
    SingletonComponent::class)
@Module

object AppModule{
    private const val BASE_URL = "https://gestionhuacalesapi.azurewebsites.net/"
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideGastosApi(retrofit: Retrofit): GastosApi {
        return retrofit.create(GastosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGastosRepository(remoteDataSource: GastosRemoteDataSource, api: GastosApi ): GastosRepository {
        return GastosRepositoryImpl(remoteDataSource, api)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("BASE_URL")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}