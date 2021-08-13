package kr.clean.devoks.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.clean.devoks.domain.model.*

interface AuthRepository {

    suspend fun userJoin(reqUserJoin: ReqUserJoin): Flow<DomainDTO<ResUserJoin>>

    suspend fun userLogin(reqUserLogin: ReqUserLogin): Flow<DomainDTO<ResUserLogin>>

}