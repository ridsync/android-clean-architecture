package kr.clean.devoks.core.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.core.context.ConstData
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.data.source.local.UserLocalDataSource
import kr.clean.devoks.data.source.local.datastore.AppDataStoreImpl
import kr.clean.devoks.data.source.local.room.CleanArchDataBase
import kr.clean.devoks.data.source.local.room.UserDataSourceImpl
import javax.inject.Singleton

/**
 * Created by devoks
 * Description : 데이터 저장 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): CleanArchDataBase {

        return Room.databaseBuilder(
            context,
            CleanArchDataBase::class.java,
            ConstData.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: CleanArchDataBase) = db.getUserDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindDataStorePref(
        appDataStore: AppDataStoreImpl
    ): AppDataStore

    @Singleton
    @Binds
    abstract fun bindChatDataSource(
        chatDataStore: UserDataSourceImpl
    ): UserLocalDataSource

}