package kr.clean.devoks.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.clean.devoks.data.source.local.GenderConverters
import kr.clean.devoks.data.source.local.room.dao.UserDao
import kr.clean.devoks.data.source.local.room.entity.User

/**
 * Created by devoks
 * Description :
 */
@Database(version = 1, entities = [User::class], exportSchema = true)
@TypeConverters(GenderConverters::class)
abstract class CleanArchDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}