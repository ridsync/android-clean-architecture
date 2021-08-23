package kr.clean.devoks.data.source.local.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kr.clean.devoks.data.source.local.room.entity.User

/**
 * Created by devoks
 * Description :
 */
@Dao
abstract class UserDao {

    @Query("SELECT * FROM users")
    abstract suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE user_idx = :userIdx")
    abstract fun getUser(userIdx: String): Flow<User>

    suspend fun getUserDistinctUntilChanged(username:String) =
        getUser(username).distinctUntilChanged()

    @Transaction
    open suspend fun setLoggedInUser(loggedInUser: User) {
        deleteUser(loggedInUser)
        insertUser(loggedInUser)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUsers(vararg user: User) : List<Long>

    @Update
    abstract suspend fun updateUser(user: User)

    @Update
    abstract suspend fun deleteUser(user: User)


    suspend fun insertOrUpdate(entity: User): Long {
        return if (entity.id == 0) {
            insertUser(entity)
        } else {
            updateUser(entity)
            entity.id.toLong()
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<User>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }
}