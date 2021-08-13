package kr.clean.devoks.core

import android.app.Application
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import kr.clean.devoks.core.logging.OKLogger
import kr.clean.devoks.data.source.local.AppDataStore
import javax.inject.Inject

/**
 * Created by devoks
 */
@HiltAndroidApp
class DevOKsApplication : Application() {

    @Inject
    lateinit var appDataStore: Lazy<AppDataStore>

    override fun onCreate() {
        super.onCreate()
        OKLogger.init()
        appDataStore.get().initAppInfos()
    }
}