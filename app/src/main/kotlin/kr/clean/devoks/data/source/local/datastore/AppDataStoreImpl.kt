package kr.clean.devoks.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kr.clean.devoks.core.context.*
import kr.clean.devoks.core.di.CoroutinesDispatcherProvider
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.data.source.local.AppDataStore
import javax.inject.Inject

/**
 * Created by devoks
 * Description : Android AppInfo,UserInfo,DataStore Provider
 */
class AppDataStoreImpl @Inject constructor(
    dispatcherProvider: CoroutinesDispatcherProvider,
    @ApplicationContext private val context: Context,
    val appInfo: AppInfo
) : AppDataStore {

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     *  TODO Experimental Code
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { context , throwable ->
        Logger.w("$context ${throwable.message}")
        if(ConstApp.isDevMode())
            throwable.printStackTrace()
    }
    private val storeCoContext = dispatcherProvider.io + coroutineExceptionHandler

    init {
        Logger.d("AppDataStoreImpl Init application = $context")
    }

    override fun initAppInfos() {
        CoroutineScope(storeCoContext).launch {
            Logger.i("[AppDataStore] InitAppInfos")
        }
    }

    override fun getAppLangCode(): String {
        return appInfo.getLangCode()
    }

    override fun isLangKorean(): Boolean {
        return appInfo.getLangCode() == "ko"
    }

    override fun getAppVersionCode() : Int{
        return appInfo.getVersionCode()
    }

    override fun getAppVersionName() : String {
        return appInfo.versionName
    }

    override fun getUserAgent() : String {
        return appInfo.userAgent
    }

    /***
     * clears all the stored data
     */
    override suspend fun clearAllAppSettings() {
        context.prefAppSetting.edit {
            it.clear()
        }
    }

    override suspend fun clearAllUserConfigs() {
        context.prefUserConfig.edit {
            it.clear()
        }
    }

}
