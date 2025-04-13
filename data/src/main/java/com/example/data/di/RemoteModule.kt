package com.example.data.di

import com.example.data.remote.ApiService
import com.example.data.remote.CargoApiService
import com.example.data.remote.CargoRemoteDataSource
import com.example.data.remote.TokenRemoteDataSource
import com.example.data.repository.CargoRepositoryImpl
import com.example.data.repository.TokenRepositoryImpl
import com.example.domain.repository.CargoRepository
import com.example.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cargo.free.beeceptor.com/")
//            .baseUrl("https://backend.insighttech.ir/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAssetRemoteDataSource(apiService: ApiService): TokenRemoteDataSource {
        return TokenRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideAssetRepository(remoteDataSource: TokenRemoteDataSource): TokenRepository {
        return TokenRepositoryImpl(remoteDataSource)
    }

    // Cargo
    @Provides
    @Singleton
    fun provideCargoApiService(retrofit: Retrofit): CargoApiService {
        return retrofit.create(CargoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoRemoteDataSource(apiService: CargoApiService): CargoRemoteDataSource {
        return CargoRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideCargoRepository(remoteDataSource: CargoRemoteDataSource): CargoRepository {
        return CargoRepositoryImpl(remoteDataSource)
    }
}