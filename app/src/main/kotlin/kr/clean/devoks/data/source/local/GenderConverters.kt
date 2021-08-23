package kr.clean.devoks.data.source.local

import androidx.room.TypeConverter
import kr.clean.devoks.data.source.local.room.entity.User

/**
 * Created by devoks
 * Description :
 */
object GenderConverters {

    @TypeConverter
    fun toGenderType(code: Int?): User.GenderType? {
        return if (code == null) null else User.GenderType.valueOf(code)
    }

    @TypeConverter
    fun toCode(genderType: User.GenderType?): Int? {
        return genderType?.code
    }
}