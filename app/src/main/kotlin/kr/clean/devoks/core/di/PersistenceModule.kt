package kr.clean.devoks.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.data.source.local.datastore.AppDataStoreImpl
import javax.inject.Singleton

/**
 * Created by devoks
 * Description : 데이터 저장 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindDataStorePref(
        appDataStore: AppDataStoreImpl
    ): AppDataStore

}