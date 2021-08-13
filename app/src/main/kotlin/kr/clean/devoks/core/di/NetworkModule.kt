package kr.clean.devoks.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.context.ConstData
import kr.clean.devoks.core.context.ConstData.CONNECT_TIMEOUT
import kr.clean.devoks.core.context.ConstData.READ_TIMEOUT
import kr.clean.devoks.core.context.ConstData.WRITE_TIMEOUT
import kr.clean.devoks.data.source.remote.AuthRemoteDataSource
import kr.clean.devoks.data.source.remote.grpc.*
import kr.clean.devoks.data.source.remote.retrofit.api.AuthAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by devoks
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
//            addInterceptor(get(named("appIntc")))
//            addInterceptor(get(named("netIntceptor")))
//            authenticator(get(named("tokenAthenticator")))
//            addInterceptor(HttpLoggingInterceptor().apply {
//                level = if (ConstsData.isDevMode()) {
//                    HttpLoggingInterceptor.Level.BODY
//                } else {
//                    HttpLoggingInterceptor.Level.NONE
//                }
//            })
        }.build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okdApplication: DevOKsApplication, okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstData.API_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(retrofit: Retrofit) = retrofit.create(AuthAPI::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSource: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource
}