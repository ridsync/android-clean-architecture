package kr.clean.devoks.core.logging

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import kr.clean.devoks.core.context.ConstApp

/**
 * Created by devoks
 * Description : Logging Usages
 * 1. v : UI Layer (Presentation)
 * 2. d : Domain Layer (Development)
 * 3. i : Data Layer (Infrastructure)
 * 4. w : Throwable (Exception)
 */
object OKLogger {

    val Logger: Logger by lazy {
        XLog.tag("[DevOKs]")
        .build()
    }

    val LoggerNet: Logger by lazy {
        XLog.tag("[DevOKs:Net]")
            .build()
    }
    val LoggerBill: Logger by lazy {
        XLog.tag("[DevOKs:Bill]")
                .build()
    }

    val LoggerLife: Logger by lazy {
        XLog.tag("[LifeCycle]")
                .build()
    }

    fun init() {
        val config = LogConfiguration.Builder()
            .logLevel(if (ConstApp.isDevMode()) LogLevel.ALL else LogLevel.NONE)
            .tag("[DevOKs]")
            .enableThreadInfo() // Enable thread info, disabled by default
            .enableStackTrace(1) // Enable stack trace info with depth 2, disabled by default
            .enableBorder() // Enable border, disabled by default
            .borderFormatter(OKBorderFormatter()) // Default: DefaultBorderFormatter
            .build()
        val androidPrinter = AndroidPrinter(false)
        XLog.init(config,androidPrinter)
    }
}