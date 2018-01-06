package com.themegalith.bks.cerd.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.themegalith.bks.cerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.cerd.network.CoinbinApi
import com.themegalith.bks.cerd.presentation.model.TickerModel
import com.themegalith.bks.cerd.utils.TickerMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by allan on 30/12/17.
 */
class MainActivityViewModel  @Inject constructor(val interactor: TickerGetterInteractor) : ViewModel() {
    private var tickers: MutableLiveData<MutableList<TickerModel>> = MutableLiveData()
    private var loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    private var subscription: Disposable? = null
    private var throwable: MutableLiveData<Throwable> = MutableLiveData()

    fun getTicker(): LiveData<MutableList<TickerModel>> {
        loadTicker(interactor.execute())
        return tickers
    }

    fun getThrowable() : LiveData<Throwable> {
        return throwable
    }

    fun getLoadingStatus() : LiveData<Boolean>{
        return loadingStatus
    }

    fun disposeSub() {
        subscription?.let { it.dispose() }
    }

    private fun loadTicker(single: Single<List<CoinbinApi.Ticker>>?) {
        single?.
        doOnSubscribe {loadingStatus.value = true}?.
        observeOn(AndroidSchedulers.mainThread())?.
        doAfterTerminate { loadingStatus.value = false }?.
        subscribe({
            success ->
                Timber.d("Updating LiveData..")
                tickers.postValue(
                        success.map { TickerMapper.convert(it) }.sortedBy { it.symbol } as MutableList<TickerModel>?
                )
                Timber.d("Updating LiveData...END")
        },
        {
            error -> throwable.postValue(error)
        })
    }

}