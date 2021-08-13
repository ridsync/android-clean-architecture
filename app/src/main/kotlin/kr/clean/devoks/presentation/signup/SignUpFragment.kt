package kr.clean.devoks.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.clean.devoks.R
import kr.clean.devoks.databinding.FragmentSignupBinding
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.model.ReqUserJoin
import kr.clean.devoks.domain.state.SignUpActionState
import kr.clean.devoks.presentation.base.BaseFragment


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val viewModel by viewModels<SignUpViewModel>()

    private var checkedIndexBirthDay: Int = -1
    private var checkedIndexGender: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragment = this
        binding.viewModel = viewModel
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitViews() {
        Logger.v("onInitView $this")

    }

    override fun onBackPressed(): Boolean{
        return false
    }

    override fun onSubscribeVm() {

        viewModel.signUpActionState.observe(this,{
            when(it) {
                is SignUpActionState.Failure -> {
                    processDataFailure(it.failure)
                }
                is SignUpActionState.ExpiredAuthCode -> {
                    processDataFailure(it.failure)
                }
                is SignUpActionState.SignUpSuccess -> {
                }
            }
        })
    }

    /**
     *  회원가입 서버 요청.
     */
    fun onClickStart(view: View){
        signupWithUserProfile()
    }

    private fun signupWithUserProfile(){
        val nickName = binding.etValueNickName.text.toString().trim()
        val hobby = binding.etValueHobby.text.toString().trim()
        val reqUserJoin = ReqUserJoin(nickname = nickName, hobby = hobby, number = "01234567890",code = "123456")
        viewModel.signupWithUserProfile(reqUserJoin)
    }

}