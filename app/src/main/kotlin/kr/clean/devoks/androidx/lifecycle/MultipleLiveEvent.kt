package kr.clean.devoks.androidx.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by devoks
 * Description : 여러 옵저버에게 모두 이벤트전달 하기 위한 LiveData 확장클래스
 */
class MultipleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    private val observers = mutableSetOf<Observer<in T>>()

    private val internalObserver = Observer<T> { t ->
        if (pending.compareAndSet(true, false)) {
            observers.forEach { observer ->
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observers.add(observer)

        if (!hasObservers()) {
            super.observe(owner, internalObserver)
        }
    }

    @MainThread
    override fun removeObservers(owner: LifecycleOwner) {
        observers.clear()
        super.removeObservers(owner)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        observers.remove(observer)
        super.removeObserver(observer)
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