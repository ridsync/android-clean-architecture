package kr.clean.devoks.presentation.base

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.clean.devoks.R
import kr.clean.devoks.core.context.ConstApp
import kr.clean.devoks.core.extension.getColorCompat
import kr.clean.devoks.core.logging.OKLogger
import kr.clean.devoks.data.source.local.datastore.prefAppSetting
import kr.clean.devoks.data.source.local.datastore.prefUserConfig
import kr.clean.devoks.domain.model.DomainDTO

/**
 * Created by devoks
 * Description :
 */
abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes protected val layoutResId: Int) : AppCompatActivity() {

    abstract fun onInitView()
    abstract fun onSubscribeVm()

    protected lateinit var binding: T
        private set

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { context , throwable ->
        OKLogger.Logger.w("$context ${throwable.message}")
        if(ConstApp.isDevMode())
            throwable.printStackTrace()
    }
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            prefAppSetting.data.first()
            prefUserConfig.data.first()
        }
        binding = DataBindingUtil.setContentView(this, layoutResId)
        if (savedInstanceState == null) {
            binding.lifecycleOwner = this
            onInitView()
            onSubscribeVm()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
////        OKLogger.get().d("[OKWON] onRestoreInstanceState  - $savedInstanceState")
//        super.onRestoreInstanceState(savedInstanceState)
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
////        OKLogger.get().d("[OKWON] onSaveInstanceState  - $outState")
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
////        OKLogger.get().d("[OKWON] onConfigurationChanged - $newConfig")
//        super.onConfigurationChanged(newConfig)
//    }

    fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    // TODO 각 실패 케이스별 공통 처리
    fun processDataFailure(failure: DomainDTO.Failure, callbackConfirm: (() -> Unit)? = null){

    }

    private fun showAlert(failure: DomainDTO.Failure) {
    }

    private fun showNetExceptionDialog(failure: DomainDTO.Failure) {
        when (failure.throwable){

        }
    }

    fun toggleProgressView(isShowProgress: Boolean) {
    }
}

/**
 * window - StatusBarColor, BackgroundColor 테마 스타일 변경 코드
 * Color : White
 */
fun Activity.setWindowDefaultTheme() {
    window?.run {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            statusBarColor =
                ContextCompat.getColor(context, R.color.color_bg_default)
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setBackgroundDrawable(ColorDrawable(context.getColorCompat(R.color.color_bg_default)))
    }
}

/**
 * window - StatusBarColor, BackgroundColor 테마 스타일 변경 코드
 * Color : Red
 */
fun Activity.setWindowsSplashTheme() {
    window?.run {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            statusBarColor =
                ContextCompat.getColor(context, R.color.primary)
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setBackgroundDrawable(ColorDrawable(context.getColorCompat(R.color.primary)))
    }
}