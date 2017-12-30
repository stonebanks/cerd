package com.themegalith.bks.yaccerd.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.themegalith.bks.yaccerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by allan on 30/12/17.
 */
class MainActivityViewModel  @Inject constructor(val interactor: TickerGetterInteractor) : ViewModel(){
    private var ticker : MutableLiveData<CoinMarketCapApi.Ticker>? = null

    fun getTicker() : LiveData<CoinMarketCapApi.Ticker> {
        return ticker!!
    }

    private fun loadTicker(observable : Single<List<CoinMarketCapApi.Ticker>>?) {
        if (observable != null) {
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        t -> Timber.d(t[0].symbol)
                    }, {
                        error -> Timber.e(error)
                    })
            }
        }

    fun loadTicker(){
        interactor.setFiat("CAD")
        loadTicker(interactor.execute())
    }
}