
package kr.clean.devoks.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kr.clean.devoks.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.clean.devoks.R
import kr.clean.devoks.databinding.FragmentSplashBinding
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.domain.state.AppsUserActionState
import kr.clean.devoks.presentation.base.setWindowsSplashTheme


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash){

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setWindowsSplashTheme()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragment = this
        binding.viewModel = viewModel
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitViews() {
        Logger.v("onInitView intent $this ${activity?.intent}")
        lifecycleScope.launch {
            delay(1000)
            inspectAndroidSecurity()
        }
    }

    override fun onSubscribeVm() {

        viewModel.launchAppActionState.observe(this, { state ->
            Logger.v("LaunchAppState = $state")
            when (state) {
                is AppsUserActionState.Failure -> {
                    processDataFailure(state.failure)
                }
                is AppsUserActionState.NeedToLogin -> {

                }
                is AppsUserActionState.NeedForAppUpdate -> {
                }
                is AppsUserActionState.LoginSuccess -> {

                }
                else -> { }
            }
        })
    }

    private fun inspectAndroidSecurity() {
        // TODO RootBeer and Emulator Detector
        startLaunchAppProcess()
    }

    // 서버상태체크 -> 로그인 프로세스
    private fun startLaunchAppProcess() {
        viewModel.startLaunchAppProcessUseCase()
    }

    private fun startMainFragment() {
        getMainNavController()?.navigate(R.id.action_frag_splash_to_frag_main)
    }

}