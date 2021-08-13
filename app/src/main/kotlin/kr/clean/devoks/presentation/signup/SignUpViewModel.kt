package kr.clean.devoks.presentation.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kr.clean.devoks.androidx.lifecycle.ActionLiveEvent
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.interactor.SignUpUseCase
import kr.clean.devoks.domain.model.ReqUserJoin
import kr.clean.devoks.domain.state.SignUpActionState
import kr.clean.devoks.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val application: DevOKsApplication,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel(application) {

    /**
     * Actions LiveData
     */
    val signUpActionState: ActionLiveEvent<SignUpActionState>
        get() = _signUpActionState
    private val _signUpActionState = ActionLiveEvent<SignUpActionState>()

    fun signupWithUserProfile(reqUserJoin : ReqUserJoin) {
        Logger.v("$reqUserJoin")
        viewModelScope.launch(dispatchersMain) {
            _signUpActionState.value =
                signUpUseCase.userJoin(reqUserJoin)
                    .flowOn(dispatchersIO)
                    .onStart { showProgress() }
                    .onCompletion { hideProgress() }
                    .single()
        }

    }
}