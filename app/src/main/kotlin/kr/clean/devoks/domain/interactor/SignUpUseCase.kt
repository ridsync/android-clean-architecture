package kr.clean.devoks.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kr.clean.devoks.core.context.ResCode
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.domain.model.DomainDTO
import kr.clean.devoks.domain.model.ReqUserJoin
import kr.clean.devoks.domain.repository.AuthRepository
import kr.clean.devoks.domain.state.SignUpActionState
import javax.inject.Inject

/**
 * Created by devoks
 * 회원 가입 유즈케이스 BL
 * Description : 회원가입 API 수행
 */
// TODO @Experimental UseCase in Domain BL
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val appDataStore: AppDataStore
) {

    suspend fun userJoin(reqUserJoin: ReqUserJoin): Flow<SignUpActionState> {
        return authRepository.userJoin(reqUserJoin)
            .transform { result ->
                when (result) {
                    is DomainDTO.Success -> {
                        emit(SignUpActionState.SignUpSuccess(result.data))
                        Logger.d("[SignUp] Success ${result.data}")
                    }
                    is DomainDTO.Failure -> {
                        when (result.code) {
                            ResCode.COMM_AUTH_CODE_EXPIRE -> {
                                emit(SignUpActionState.ExpiredAuthCode(DomainDTO.Failure(throwable = result.throwable)))
                            }
                            else -> {
                                emit(SignUpActionState.Failure(DomainDTO.Failure(throwable = result.throwable)))
                            }
                        }
                        Logger.d("[SignUp] Failure $result")
                    }
                }
            }
    }

}