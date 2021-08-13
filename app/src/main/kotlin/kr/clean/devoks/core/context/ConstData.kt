package kr.clean.devoks.core.context

import kr.clean.devoks.BuildConfig
import java.net.HttpURLConnection

/**
 * Created by devoks
 * Description : 프로젝트 Data (Repository) 관련 전역 상수 정의
 */

/**
 * Description : Response Code Define
 */
object ConstData {

    // Server API
    private const val SERVER_DOMAIN = "okdomain.com"
    private const val URL_PROTOCOL = "http://"
    private const val SERVER_API_PATH = "/api/"
    const val API_SERVER_URL = URL_PROTOCOL + SERVER_DOMAIN

    // Persistence
    const val PREF_NAME_APP_SETTINGS = "spf_app_settings"
    const val PREF_NAME_USER_CONFIGS = "spf_user_configs"

    const val CONNECT_TIMEOUT = 15L
    const val WRITE_TIMEOUT = 15L
    const val READ_TIMEOUT = 30L

}

/**
 * Description : Request Code & Parameter Key
 */
object ReqCode {

    // Server Metadata Key
    const val KEY_AUTHORIZION = "authorization"
    const val FORMAT_BEARER_PREFIX = "Bearer %s"
    const val KEY_LANG = "lang"
    const val KEY_DEVICE_TYPE = "device_type"
    const val KEY_USER_AGENT = "user_agent"

    // Server API Param Key

}


object ResCode {

    /**
     *  HTTP Status Code
     */
    const val HTTP_CODE_401 = HttpURLConnection.HTTP_UNAUTHORIZED // Not Authorization
    const val HTTP_CODE_404 = HttpURLConnection.HTTP_NOT_FOUND // Not Found
    const val HTTP_CODE_408 = HttpURLConnection.HTTP_CLIENT_TIMEOUT // Request Time Out

    /**
     *  Server Response Code
     *  @sample 'Type_Noun_Verb'
     */
    // Common
    const val APP_NOCONNECTIVITY = -111 // Internet Connection ERROR
    const val APP_CLIENT_ERROR = -112 // Client Exception ERROR
    const val APP_HTTP_ERROR = -113 // Retrofit Successfull 300 Over - HTTP ERROR
    const val APP_RPC_ERROR = -114 // gRPC - ERROR Except for Status:OK

    const val COMM_SUCCESS = 0 // 200 정상 데이터 송수신

    // Join
    const val COMM_AUTH_CODE_EXPIRE = 2018 // 인증코드 료
}

