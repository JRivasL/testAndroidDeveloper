package com.rivas.testandroiddeveloper.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rivas.testandroiddeveloper.api.ApiService
import com.rivas.testandroiddeveloper.di.calladapter.CoroutineCallAdapterFactory
import com.rivas.testandroiddeveloper.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class RetrofitModule {

    @Provides
    @Reusable
    fun providerApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Reusable
    fun providerRepository(dataSource: Repository.Network):
            Repository = dataSource

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create(gson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client())
        .build()

    private fun client(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

        val okHttpClient = OkHttpClient
            .Builder()
            .connectionSpecs(arrayListOf(ConnectionSpec.COMPATIBLE_TLS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }. addInterceptor(interceptor)
            .connectionSpecs(specs)
        return okHttpClient.build()
    }
}