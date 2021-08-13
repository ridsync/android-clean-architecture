package kr.clean.devoks.data.mapper

import androidx.datastore.preferences.protobuf.Timestamp
import org.mapstruct.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


/**
 * Created by devoks
 * Description : Automatic Mapper by mapstruct
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class StructMapperBase {

     fun mapDate(timestamp: Timestamp): LocalDateTime {
         return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.seconds,
             timestamp.nanos.toLong()
         ), ZoneId.systemDefault())
     }

}