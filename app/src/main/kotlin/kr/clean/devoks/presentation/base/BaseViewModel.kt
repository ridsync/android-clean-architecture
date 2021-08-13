package kr.clean.devoks.presentation.base

import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kr.clean.devoks.androidx.lifecycle.MultipleLiveEvent
import kr.clean.devoks.androidx.lifecycle.ActionLiveEvent
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.context.ConstApp
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.model.DomainDTO

/**
 * Created by devoks
 * Description :
 */
open class BaseViewModel(application: DevOKsApplication) : AndroidViewModel(application) {

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { context , throwable ->
        Logger.w("$context ${throwable.message}")
        if(ConstApp.isDevMode())
            throwable.printStackTrace()
        // TODO
        failureResource.value = DomainDTO.Failure(-1, message = "앱 예외 발생", throwable = null)
    }

    /**
     * Declared Dispatchers (Dispatchers + CoroutineExceptionHandler)
     */
    protected val dispatchersIO = Dispatchers.IO + coroutineExceptionHandler
    protected val dispatchersMain = Dispatchers.Main + coroutineExceptionHandler

    // TODO
    val dataLoading: MultipleLiveEvent<Boolean>
        get() = _dataLoading
    protected val _dataLoading: MultipleLiveEvent<Boolean> = MultipleLiveEvent()

    val dataProgress: MultipleLiveEvent<Boolean>
        get() = _dataProgress
    protected val _dataProgress: MultipleLiveEvent<Boolean> = MultipleLiveEvent()

    val failureResource: ActionLiveEvent<DomainDTO.Failure>
        get() = _failureResource
    protected val _failureResource: ActionLiveEvent<DomainDTO.Failure> = ActionLiveEvent()

//
//    val onNavScreen: SingleLiveEvent<NavScreen<ScreenInfo>>
//        get() = inNaviScreen
//    protected val inNaviScreen: SingleLiveEvent<NavScreen<ScreenInfo>> = SingleLiveEvent()

    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     */
    protected val vmComDisposables = CompositeDisposable()

    override fun onCleared() {
        vmComDisposables.clear()
        super.onCleared()
    }


    /**
     *  데이터 Fetch(Get) 위한 로딩UI
     *  UI 형태 : SwipeRefreshLayout ...
     */
    protected fun showLoading() {
        dataLoading.value = true
    }
    protected fun hideLoading() {
        dataLoading.value = false
    }
    /**
     *  데이터 Upload(Post) 위한 프로그레스UI
     *  UI 형태 : Android ProgressBar
     */
    protected fun showProgress() {
        dataProgress.value = true
    }
    protected fun hideProgress() {
        dataProgress.value = false
    }

}