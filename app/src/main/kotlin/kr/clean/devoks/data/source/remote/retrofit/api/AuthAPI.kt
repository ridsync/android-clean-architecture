package kr.clean.devoks.data.source.remote.retrofit.api

import kotlinx.coroutines.flow.Flow
import kr.clean.devoks.data.source.remote.retrofit.model.ResBase
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserJoin
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserLogin
import kr.clean.devoks.domain.model.ReqUserJoin
import kr.clean.devoks.domain.model.ReqUserLogin
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by devoks
 */
interface AuthAPI {

    @FormUrlEncoded
    @POST("/v1auth/join")
    fun postJoin(@Body reqUserJoin: ReqUserJoin): Flow<ResBase<ResModelUserJoin>>

    @FormUrlEncoded
    @POST( "/v1auth/login")
    fun postLogin(@Body reqUserLogin: ReqUserLogin): Flow<ResBase<ResModelUserLogin>>

}