package kr.clean.devoks.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.context.ConstApp
import kr.clean.devoks.data.source.local.AppDataStore
import kr.clean.devoks.domain.interactor.LaunchAppUseCase
import kr.clean.devoks.domain.state.AppsUserActionState
import kr.clean.devoks.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: DevOKsApplication,
    private val launchAppUseCase: LaunchAppUseCase,
    private val appDataStore: AppDataStore,
) : BaseViewModel(application) {

    /**
     * Views LiveData
     */
    val versionName: StateFlow<String>
        get() = _versionName
    private val _versionName = MutableStateFlow("")
    /**
     * Actions LiveData
     */
    val launchAppActionState: MutableLiveData<AppsUserActionState>
        get() = _launchAppActionState
    private val _launchAppActionState = MutableLiveData<AppsUserActionState>()

    /**
     * Others MemberFields
     */

    init {
        viewModelScope.launch(dispatchersMain) {
            if(ConstApp.isDevMode()) // 앱 버젼 표시
                _versionName.value = appDataStore.getAppVersionName()
        }
    }

    fun startLaunchAppProcessUseCase(){
        viewModelScope.launch(dispatchersMain) {
            val verCode = appDataStore.getAppVersionCode()
            _launchAppActionState.value = launchAppUseCase.launchAppProcess(verCode)
                .flowOn(dispatchersIO)
                .onStart { showLoading() }
                .onCompletion { hideLoading() }
                .single()
        }
    }

}