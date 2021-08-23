package kr.clean.devoks.domain.interactor

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.model.DomainDTO
import kr.clean.devoks.domain.model.ReqUserLogin
import kr.clean.devoks.domain.repository.AuthRepository
import kr.clean.devoks.domain.state.AppsUserActionState
import javax.inject.Inject

/**
 * Created by devoks
 * 회원 가입 유즈케이스 BL
 * Description : 회원가입 API 수행
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun userLogin() = flow {
        val reqUserLogin = ReqUserLogin(
            accessToken = "my accessToken",
            pushToken = "my FCM pushToken",
            adId = "my AdId")
        val resultLogin = authRepository.userLogin(reqUserLogin).single()
        Logger.d("[Login] Result \n$resultLogin")
        when(resultLogin) {
            is DomainDTO.Success -> {
                // 로그인 결과에 따라 토큰정보 저장, Status 에따른 분기처리
                val userStatus = resultLogin.data.status
                when (userStatus.blockType) {
                    "Success" -> {
                        Logger.d("[Login] LoginSuccess")
                        emit(AppsUserActionState.LoginSuccess)
                    }
                    else -> {
                        emit(AppsUserActionState.NeedToLogin)
                    }
                }
            }
            is DomainDTO.Failure -> {
                emit(AppsUserActionState.Failure(resultLogin))
            }
        }
    }

}