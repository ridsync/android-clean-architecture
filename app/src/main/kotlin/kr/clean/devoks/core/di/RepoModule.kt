package kr.clean.devoks.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.clean.devoks.data.repository.AuthRepositoryImpl
import kr.clean.devoks.domain.repository.AuthRepository
import javax.inject.Singleton

/**
 * Created by devoks
 * Description :
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository
}