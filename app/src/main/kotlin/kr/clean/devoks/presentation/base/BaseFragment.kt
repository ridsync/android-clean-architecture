package kr.clean.devoks.presentation.base

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kr.clean.devoks.core.context.ConstApp
import kr.clean.devoks.core.extension.hideKeyboard
import kr.clean.devoks.core.extension.ifLets
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.model.DomainDTO

/**
 * Created by devoks
 * Description :
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : BaseLogFragment() {

    abstract fun onInitViews()
    abstract fun onSubscribeVm()

    protected val compositeDisposable = CompositeDisposable()

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { context , throwable ->
        Logger.w("$context ${throwable.message}")
        if(ConstApp.isDevMode())
            throwable.printStackTrace()
    }
    protected val dispatchersMain = Dispatchers.Main + coroutineExceptionHandler
    protected val dispatchersIO = Dispatchers.IO + coroutineExceptionHandler

    protected lateinit var binding: T
        private set

    private var rootView: View? = null
    private var isFirstInit = true
    private var isHideKeyboardByTouchEvent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setWindowDefaultTheme()
        setWindowSoftInputMode()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (rootView == null) {
            DataBindingUtil.inflate<T>(inflater, layoutResId, container, false).apply{
                binding = this
                rootView = this.root
            }.root
        } else {
            rootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        if (isFirstInit) {
            isFirstInit = false
            onInitViews()
            onSubscribeVm()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Logger.v("onConfigurationChanged  -  $newConfig")
        super.onConfigurationChanged(newConfig)
    }

    /**
     * 앱 백키 제어 위한 콜백 분기 처리
     * 각 화면별로 Override
     */
    open fun onBackPressed(): Boolean {
        return true
    }

    protected fun finish() {
        getMainNavController()?.navigateUp()
    }

    protected fun getMainNavController(): NavController? =
        activity?.supportFragmentManager?.primaryNavigationFragment?.findNavController()

    protected fun processDataFailure(
        failure: DomainDTO.Failure,
        callbackAppError: (() -> Unit)? = null
    ) {
        (activity as? BaseActivity<*>)?.processDataFailure(failure, callbackAppError)
    }

    protected fun setWindowSoftInputMode(mode: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN){
        activity?.window?.setSoftInputMode(mode)
    }

    /**
     * SoftKeyboard Touch Control from TouchEvent
     */
    fun setHideKeyboardByTouchEvent (isTouchControl: Boolean) {
        isHideKeyboardByTouchEvent = isTouchControl
    }

    fun dispatchTouchEvent(ev: MotionEvent) {
        ifLets(isHideKeyboardByTouchEvent){
            val focusView: View? = activity?.currentFocus
            if (focusView != null) {
                val rect = Rect()
                focusView.getGlobalVisibleRect(rect)
                val x = ev.x.toInt()
                val y = ev.y.toInt()
                if (!rect.contains(x, y)) {
                    focusView.hideKeyboard()
                }
            }
        }
    }
}