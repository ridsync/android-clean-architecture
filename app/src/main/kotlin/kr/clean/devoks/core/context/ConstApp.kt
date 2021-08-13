package kr.clean.devoks.core.context

import kr.clean.devoks.BuildConfig

/**
 * Created by devoks
 * Description : 안드로이드 전역 상수
 */
class ConstApp {

    companion object {
        fun isDevMode(): Boolean {
            return BuildConfig.DEBUG
        }
    }
}
