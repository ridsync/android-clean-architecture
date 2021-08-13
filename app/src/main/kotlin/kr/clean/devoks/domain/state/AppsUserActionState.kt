package kr.clean.devoks.domain.state

import kr.clean.devoks.domain.model.DomainDTO
import kr.clean.devoks.domain.model.ResAppStatus

/**
 * Created by devoks
 * Description : 유즈케이스별 비즈니스로직 액션(상태) 정보 클래스
 */
sealed class AppsUserActionState {
    data class Failure(val failure: DomainDTO.Failure) : AppsUserActionState()

    /**앱 업데이트 필요*/
    data class NeedForAppUpdate(val appStatus: ResAppStatus) : AppsUserActionState()

    /**로그인 필요함 (토큰 미존재/토큰 만료)*/
    object NeedToLogin : AppsUserActionState()

    /**로그인 성공*/
    object LoginSuccess : AppsUserActionState()

}