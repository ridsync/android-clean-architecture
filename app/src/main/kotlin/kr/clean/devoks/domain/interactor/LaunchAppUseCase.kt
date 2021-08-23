package kr.clean.devoks.domain.interactor

import kotlinx.coroutines.flow.*
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.domain.model.DomainDTO
import kr.clean.devoks.domain.repository.AuthRepository
import kr.clean.devoks.domain.state.AppsUserActionState
import javax.inject.Inject

/**
 * Created by devoks
 * 앱 시작 프로세스 BL
 * Description : 앱서버상태 체크, 로그인 프로세스
 */
// TODO @Experimental UseCase in Domain BL
class LaunchAppUseCase @Inject constructor(
    private val suthRepository: AuthRepository,
    private val loginUserCase: LoginUseCase,
    private val dataStore: AppDataStore
) {

    suspend fun launchAppProcess(versionCode: Int) = flow {
        // 앱 상태 체크 로직 TODO
        if(versionCode > 0) {
            emit(loginUserCase.userLogin().single())
        } else {
            emit(AppsUserActionState.Failure(DomainDTO.Failure(throwable = null)))
        }
    }

}