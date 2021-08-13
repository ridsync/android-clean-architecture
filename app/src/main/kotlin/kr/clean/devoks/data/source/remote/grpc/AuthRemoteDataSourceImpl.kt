package kr.clean.devoks.data.source.remote.grpc

import kotlinx.coroutines.flow.*
import kr.clean.devoks.core.di.CoroutinesDispatcherProvider
import kr.clean.devoks.data.source.remote.AuthRemoteDataSource
import kr.clean.devoks.data.source.remote.retrofit.api.AuthAPI
import kr.clean.devoks.data.source.remote.retrofit.model.ResBase
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserJoin
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserLogin
import kr.clean.devoks.domain.model.*
import javax.inject.Inject


/**
 * Created by devoks
 * Description :
 */
class AuthRemoteDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : AuthRemoteDataSource {

    override suspend fun userJoin(request: ReqUserJoin): Flow<ResBase<ResModelUserJoin>> {
        return authAPI.postJoin(request)
    }

    override suspend fun userLogin(request: ReqUserLogin): Flow<ResBase<ResModelUserLogin>> {
        return authAPI.postLogin(request)
    }

}