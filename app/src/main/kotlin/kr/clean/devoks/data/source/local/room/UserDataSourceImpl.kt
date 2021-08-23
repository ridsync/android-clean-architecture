package kr.clean.devoks.data.source.local.room

import kotlinx.coroutines.flow.Flow
import kr.clean.devoks.data.source.local.UserLocalDataSource
import kr.clean.devoks.data.source.local.room.dao.UserDao
import kr.clean.devoks.data.source.local.room.entity.User
import javax.inject.Inject


/**
 * Created by okwon on 2021/05/26.
 * Description :
 */
class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserLocalDataSource {

    override suspend fun insertNewUserData(nickName: String) {
            userDao.insertUser(User(userIdx = 104,nickName = nickName,gender = User.GenderType.FEMALE))
    }

    override fun getUserData(userIdx: String): Flow<User> {
        return userDao.getUser(userIdx = userIdx)
    }
}