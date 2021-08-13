package kr.clean.devoks.domain.model

import kr.clean.devoks.core.context.ResCode


/**
 * Created by devoks
 * Description : DomainLayer Data Model
 */
sealed class DomainDTO<out T : Any> {
    data class Success<out T : Any>(val data: T) : DomainDTO<T>()
    data class Failure(val code: Int = -1, var message: String? = null, val throwable: Throwable?) : DomainDTO<Nothing>() {
        fun isAppError(): Boolean = code > ResCode.COMM_SUCCESS && throwable == null
    }
}
