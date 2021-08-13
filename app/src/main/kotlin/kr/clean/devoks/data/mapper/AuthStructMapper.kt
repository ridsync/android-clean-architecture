package kr.clean.devoks.data.mapper

import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserJoin
import kr.clean.devoks.data.source.remote.retrofit.model.ResModelUserLogin
import kr.clean.devoks.domain.model.*
import org.mapstruct.*


/**
 * Created by devoks
 * Description : Automatic Mapper by mapstruct
 */
@Mapper(uses = [StructMapperBase::class],
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
abstract class AuthStructMapper{

    abstract fun mapEntityToDomain(response: ResModelUserJoin): ResUserJoin

    abstract fun mapEntityToDomain(response: ResModelUserLogin): ResUserLogin

}
