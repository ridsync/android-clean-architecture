package kr.clean.devoks.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Created by devoks
 * Description : Data Layer Entity Model
 */
@Entity(tableName = User.TABLE_NAME, indices = [
    Index(value = [User.COLUMN_USER_IDX],unique = true),
    Index(value = [User.COLUMN_NICKNAME],unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) var id: Int = 0,
    @ColumnInfo(name = COLUMN_USER_IDX) val userIdx: Long,
    @ColumnInfo(name = COLUMN_NICKNAME) val nickName: String,
    @ColumnInfo(name = COLUMN_GENDER) val gender: GenderType,
) {

    companion object {
        const val TABLE_NAME = "users"
        /** The name of the ID column.  */
        const val COLUMN_ID = "id"
        /** The name of the name column.  */
        const val COLUMN_USER_IDX = "user_idx"
        const val COLUMN_NICKNAME = "nickname"
        const val COLUMN_GENDER = "gender"
    }

    enum class GenderType(val code: Int){
        ALL(1),
        MALE(2),
        FEMALE(3);

        companion object {
            private const val ADDING:Int = 1 // value Added Default Position
            fun valueOf(code: Int?) = values().firstOrNull { it.ordinal+ADDING == code }
        }
    }
}