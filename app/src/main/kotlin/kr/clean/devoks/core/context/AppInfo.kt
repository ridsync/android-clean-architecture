package kr.clean.devoks.core.context

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kr.clean.devoks.BuildConfig
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.extension.formatSafe
import kr.clean.devoks.core.logging.OKLogger.Logger
import java.util.*

/**
 * Created by devoks
 * Description : 앱,디바이스 관련 정보
 */
class AppInfo(private val mApplication: DevOKsApplication) {

    init {
        Logger.d("AppInfo Init application = $mApplication")
    }

    val appName: String by lazy {
        "android-clean-architecture"
    }

    val versionName : String by lazy {
        val pInfo: PackageInfo
        var vname = "0"
        try {
            val context = mApplication.applicationContext
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            vname = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        vname
    }

    val userAgent: String by lazy {
        "%s/%s-%s)".formatSafe(
            appName,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
    }

    fun getVersionCode() : Int {
        val pInfo: PackageInfo
        var code = 0
        try {
            pInfo = mApplication.packageManager.getPackageInfo(mApplication.packageName, 0)
            code = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                pInfo.longVersionCode.toInt()
            } else {
                pInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }

    fun getLangCode() : String {
        val locale = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val locales: LocaleList =
                mApplication.resources.configuration.locales
            locales[0]
        } else {
            mApplication.resources.configuration.locale
        }
        return locale.language.lowercase(Locale.getDefault())
    }

    fun getCountryCode() : String {
        val locale = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val locales: LocaleList =
                mApplication.resources.configuration.locales
            locales[0]
        } else {
            mApplication.resources.configuration.locale
        }
        return locale.country
    }

    /**
     * Android 고유 식별자 획득 (Android ID)
     */
    @SuppressLint("HardwareIds")
    fun getAndroidId() : String? {
        val androidId = Settings.Secure.getString(
            mApplication.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return try {
            val androidID = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8"))).toString()
            Logger.i("[SSAID] Android : $androidID")
            if(androidID.isNotEmpty()){
                androidID
            } else {
                // TODO Firebase Report
                getAdvertisingId()
            }
        } catch (e: Exception) {
            Logger.e(e) // TODO Firebase Report
            getAdvertisingId()
        }
    }

    /**
     * Android 광고 식별자 획득 (Advertising ID)
     */
     fun getAdvertisingId() : String {
       return try {
            val info = AdvertisingIdClient.getAdvertisingIdInfo(mApplication)
           Logger.i("[AdId] AdvertisingId : ${info.id}")
           return info.id
        } catch (e: Exception) {
           Logger.e(e)  // TODO Firebase Report
            ""
        }
    }

    /**
     * Android FCM Token 획득 (FirebaseMessaging Instance)
     */
    @ExperimentalCoroutinesApi
    suspend fun getFCMPushToken() = callbackFlow {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                trySend(token)
                Logger.i("[FCM] Result New FCM Token : $token")
            } else {
                val params = hashMapOf<String,String>()
                task.exception?.message?.let {
                    params["exception"] = it
                }
                // TODO Firebase Report
                Logger.w("[FCM] FirebaseInstanceId failed", task.exception)
                trySend(null)
            }
            close()
        }
        awaitClose {}
    }

    /**
     * 앱이 포그라운드 상테인지 확인하는 함수
     * @return boolean  ( true - 포그라운드, false - 백그라운드 )
     */
    fun isForegroundAppProcess(context: Context): Boolean {
        var isForegroundFlags = false
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        isForegroundFlags = true
                        break
                    }
                }
            }
        }
        Logger.d("[AppStatus] isForegroundAppProcess = $isForegroundFlags")
        return isForegroundFlags
    }

    fun isBackgroundAppProcess(context: Context): Boolean {
        return !isForegroundAppProcess(context)
    }

}