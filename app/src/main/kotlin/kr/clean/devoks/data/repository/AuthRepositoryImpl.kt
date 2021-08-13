package kr.clean.devoks.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kr.clean.devoks.core.di.CoroutinesDispatcherProvider
import kr.clean.devoks.data.mapper.AuthStructMapper
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.data.source.remote.AuthRemoteDataSource
import kr.clean.devoks.domain.model.*
import kr.clean.devoks.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource,
                                             private val authStMapper: AuthStructMapper,
                                             private val appDataStore: AppDataStore,
                                             private val dispatcherProvider: CoroutinesDispatcherProvider
) : AuthRepository {

    override suspend fun userJoin(reqUserJoin: ReqUserJoin): Flow<DomainDTO<ResUserJoin>> {
        return  authRemoteDataSource.userJoin(reqUserJoin).map { result ->
            DomainDTO.Success(authStMapper.mapEntityToDomain(result.data!!)) // 응답 결과 처리 임시코드
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun userLogin(reqUserLogin: ReqUserLogin): Flow<DomainDTO<ResUserLogin>> {
        return  authRemoteDataSource.userLogin(reqUserLogin).map { result ->
            DomainDTO.Success(authStMapper.mapEntityToDomain(result.data!!)) // 응답 결과 처리 임시코드
        }.flowOn(dispatcherProvider.io)
    }
}