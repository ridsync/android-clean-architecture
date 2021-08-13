package kr.clean.devoks.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.clean.devoks.data.source.remote.retrofit.model.ResBase
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserJoin
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserLogin
import kr.clean.devoks.domain.model.*

/**
 * Created by devoks
 * Description :
 */
interface AuthRemoteDataSource {

    suspend fun userJoin(request: ReqUserJoin): Flow<ResBase<ResModelUserJoin>>

    suspend fun userLogin(request: ReqUserLogin): Flow<ResBase<ResModelUserLogin>>

}