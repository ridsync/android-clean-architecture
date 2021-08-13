package kr.clean.devoks.domain.state

import kr.clean.devoks.domain.model.DomainDTO
import kr.clean.devoks.domain.model.ResUserJoin

/**
 * Created by devoks
 * Description : 유즈케이스별 비즈니스로직 액션(상태) 정보 클래스
 */
sealed class SignUpActionState {
    data class Failure(val failure: DomainDTO.Failure) : SignUpActionState()

    /**  회원가입 완료 */
    data class SignUpSuccess(val resUserJoin: ResUserJoin) : SignUpActionState()

    /** 회원가입 실패 : 인증코드 만료 */
    data class ExpiredAuthCode(val failure: DomainDTO.Failure) : SignUpActionState()

}