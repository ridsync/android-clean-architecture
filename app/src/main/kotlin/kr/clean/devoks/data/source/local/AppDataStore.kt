package kr.clean.devoks.data.source.local

/**
 * Created by devoks
 * Description :
 */
interface AppDataStore {

    /**
     *  AppInfo
     */
    fun initAppInfos()
    fun getAppLangCode(): String
    fun isLangKorean(): Boolean
    fun getAppVersionCode(): Int
    fun getAppVersionName(): String
    fun getUserAgent(): String

    /***
     * clears all the stored data
     */
    suspend fun clearAllAppSettings()
    suspend fun clearAllUserConfigs()
}
