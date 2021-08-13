package kr.clean.devoks.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.data.mapper.AuthStructMapper
import org.mapstruct.factory.Mappers
import javax.inject.Singleton


/**
 * Created by devoks
 * Description :
 */
@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideAuthMapper(): AuthStructMapper {
        return Mappers.getMapper(AuthStructMapper::class.java)
    }

}