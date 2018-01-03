package com.themegalith.bks.cerd.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.themegalith.bks.cerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.cerd.network.CoinMarketCapApi
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

    fun getTicker(): LiveData<MutableList<TickerModel>> {
        loadTicker(interactor.execute())
        return tickers
    }

    fun getLoadingStatus() : LiveData<Boolean>{
        return loadingStatus
    }

    fun disposeSub() {
        subscription?.let { it.dispose() }
    }

    private fun loadTicker(single: Single<MutableList<CoinMarketCapApi.Ticker>>?) {
        subscription = single?.doOnSubscribe {loadingStatus.value = true}?.observeOn(AndroidSchedulers.mainThread())?.doAfterTerminate { loadingStatus.value = false }?.subscribe({ t ->
            Timber.d("Updating LiveData..")
            tickers.postValue(
                    t.map { TickerMapper.convert(it)}.sortedBy { it.symbol } as MutableList<TickerModel>?
            )
            Timber.d("Updating LiveData...END")
        }, { error ->
            Timber.e(error)
        })
    }


}