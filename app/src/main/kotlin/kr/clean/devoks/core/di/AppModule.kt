package kr.clean.devoks.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.context.AppInfo
import javax.inject.Singleton


/**
 * Created by devoks
 * Description :
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkdApplication(@ApplicationContext context: Context): DevOKsApplication {
        return context as DevOKsApplication
    }

    @Singleton
    @Provides
    fun provideAppInfo(okdApplication: DevOKsApplication): AppInfo {
        return AppInfo(okdApplication)
    }

}