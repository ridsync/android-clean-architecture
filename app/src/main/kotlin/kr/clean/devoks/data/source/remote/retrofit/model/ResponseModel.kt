package kr.clean.devoks.data.source.remote.retrofit.model

import com.google.gson.annotations.SerializedName
import kr.clean.devoks.core.context.ResCode
import kr.clean.devoks.domain.model.UserStatus

/**
 * Created by okwon on 2021/08/12.
 * Description :
 */

data class ResBase<T>(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String,
    @SerializedName("debug") val debug: Debug?,
    @SerializedName("data") val data: T?
) {

    fun isSuccess(): Boolean {
        return code == ResCode.COMM_SUCCESS
    }
}

data class Debug(
    @SerializedName("exception") val exception: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("file") val file: String,
    @SerializedName("line") val line: Int
)

data class ResModelUserJoin(
    val accessToken: String,
)

data class ResModelUserLogin(
    val idx: Long,
    val accessToken: String,
    val status: UserStatus
)