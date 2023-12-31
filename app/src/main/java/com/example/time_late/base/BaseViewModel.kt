//package com.example.time_late.views.base
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.time_late.views.commmon.Event
//import java.util.concurrent.TimeUnit
//
//@Suppress("MemberVisibilityCanBePrivate", "PropertyName", "unused")
//abstract class BaseViewModel : ViewModel() {
//
//    val disposable = CompositeDisposable()
//
//    var isOnLoading = false
//
//    protected val _onBackPressed = MutableLiveData<Event<Unit>>()
//    val onBackPressed: LiveData<Event<Unit>> get() = _onBackPressed
//
//    private val _onLoading = MutableLiveData<Event<Unit>>()
//    val onLoading: LiveData<Event<Unit>> get() = _onLoading
//
//    private val _onFinishLoading = MutableLiveData<Event<Unit>>()
//    val onFinishLoading: LiveData<Event<Unit>> get() = _onFinishLoading
//
//    override fun onCleared() {
//        super.onCleared()
//        disposable.clear()
//    }
//
//    fun onLoading() {
//        isOnLoading = true
//        _onLoading.value = Event(Unit)
//    }
//
//    fun onFinishLoading() {
//        isOnLoading = false
//        _onFinishLoading.value = Event(Unit)
//    }
//
//    fun onBackPressed() {
//        _onBackPressed.value = Event(Unit)
//    }
//
//    // TODO for bottom navigation [
//
//    // TODO for bottom navigation ]
//
//    protected fun Disposable.addTo(disposable: CompositeDisposable) = disposable.add(this)
//    protected fun <T> MutableLiveData<T>.post(value: T?, delay: Long) = apply {
//        Single.timer(delay, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { _ ->
//                postValue(value)
//            }.addTo(disposable)
//    }
//}
