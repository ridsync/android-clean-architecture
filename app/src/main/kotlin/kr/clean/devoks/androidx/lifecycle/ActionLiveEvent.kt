package kr.clean.devoks.androidx.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kr.clean.devoks.core.logging.OKLogger.Logger
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by devoks
 * Description : Action LiveData Class
 *  - input value에 의한 이벤트 전달만을 허용 하기 위한 LiveData 확장클래스
 */
class ActionLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Logger.w("Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            } else {
                Logger.d("Observer onChanged Not Called ")
            }
        })
    }

    override fun postValue(value: T?) {
        pending.set(true)
        super.postValue(value)
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}