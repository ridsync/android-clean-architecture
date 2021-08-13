package kr.clean.devoks.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.clean.devoks.core.DevOKsApplication
import kr.clean.devoks.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainActViewModel @Inject constructor(
    private val application: DevOKsApplication,
) : BaseViewModel(application) {

    override fun onCleared() {
        super.onCleared()
    }
}