package kr.clean.devoks.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kr.clean.devoks.R
import kr.clean.devoks.databinding.ActivityMainBinding
import kr.clean.devoks.presentation.base.BaseActivity
import kr.clean.devoks.presentation.base.BaseFragment


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainActViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.viewModel = mainViewModel

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentFragment = getCurrentFragment()
        currentFragment?.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    // LaunchMode singleTask true 일 경우 푸시처리위해 필요함 (앱실행중)...
    // TODO findNavController 존재시에만 호출해야함 or MainFragment의 handleIntent 메소드를 호출해서 공통처리.
    fun handleIntent(intent: Intent?) {
    }

    override fun onInitView() {

    }

    override fun onSubscribeVm() {

    }

    /**
     * 백키 제어 위한 콜백 분기 처리
     */
    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if(currentFragment?.onBackPressed() == true){
            super.onBackPressed()
        }
    }
    /**
     * PrimaryNavigation - BaseFragment를 반환하는 메소드 nullable
     */
    private fun getCurrentFragment(): BaseFragment<*>? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.appHostFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.primaryNavigationFragment as? BaseFragment<*>
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getCurrentFragment()?.onActivityResult(requestCode, resultCode, data)
    }
}