package kr.clean.devoks.core.extension

import kr.clean.devoks.core.logging.OKLogger.Logger
import java.lang.Exception
import java.time.Instant
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.abs


/**
 * Created by devoks
 * Description : 시간처리 확장 함수
 */
fun Long.convertDateTime(isTimeStampMilliSec: Boolean = false) : LocalDateTime? {
    return try {
        val targetTimeStamp = if(isTimeStampMilliSec) this else this.times(1000)
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(targetTimeStamp),
            ZoneId.systemDefault()
        )
    } catch (e: Exception){
        Logger.e(e)
        null
    }
}

fun LocalDateTime.betweenDays(otherDateTime: LocalDateTime) : Int {
    return try {
        return abs(Period.between(this.toLocalDate(),otherDateTime.toLocalDate()).days)
    } catch (e: Exception){
        Logger.e(e)
        0
    }
}

fun LocalDateTime.formatDisplay(pattern: String) : String {
    return try {
        format(DateTimeFormatter.ofPattern(pattern))
    } catch (e: Exception){
        Logger.e(e)
        ""
    }
}



