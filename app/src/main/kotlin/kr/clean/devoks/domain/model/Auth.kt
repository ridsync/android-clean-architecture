package kr.clean.devoks.domain.model

import java.io.Serializable

/**
 * Created by devoks
 * Description : Authentication Model & Enum Class
 */
data class ResAppStatus(
    val appStatusMessage: String = "",
    val storeUrl: String = ""
)

data class UserStatus(
    val lastAccessTime: Long,
    val blockedTime: Long,
    val restrictedUntil: Long,
    val blockType: String,
) : Serializable

data class ReqUserJoin(
    val nickname: String,
    val hobby: String,
    val number: String,
    val code: String
)
data class ResUserJoin(
    val accessToken: String,
)

data class ResMobileAuthCode(
    var code: String? = null
)

data class ResVerfiyAuthCode(
    var existAccount: Boolean = false,
    var accessToken: String? = null
)

data class ResUpdateMobilePhone(
    var accessToken: String? = null
)

data class ReqUserLogin(
    val pushToken: String,
    val accessToken: String,
    val adId: String
)
data class ResUserLogin(
    val idx: Long,
    val accessToken: String,
    val status: UserStatus
)