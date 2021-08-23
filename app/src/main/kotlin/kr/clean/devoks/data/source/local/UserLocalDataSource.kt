package kr.clean.devoks.data.source.local

import kotlinx.coroutines.flow.Flow
import kr.clean.devoks.data.source.local.room.entity.User


/**
 * Created by okwon on 2021/05/26.
 * Description :
 */
interface UserLocalDataSource {

    suspend fun insertNewUserData(nickName: String)
    fun getUserData(idx: String) : Flow<User>
}