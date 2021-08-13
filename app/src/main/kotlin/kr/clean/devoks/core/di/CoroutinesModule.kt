package kr.clean.devoks.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by devoks
 * Description :
 */

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @Provides
    fun providesDispatcherProvider() = CoroutinesDispatcherProvider()

    @DispatcherIO
    @Provides
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

}

annotation class DispatcherIO

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {

    @Inject
    constructor() : this(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)
}