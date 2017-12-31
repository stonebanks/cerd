package com.themegalith.bks.yaccerd.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.themegalith.bks.yaccerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import com.themegalith.bks.yaccerd.presentation.model.Ticker
import com.themegalith.bks.yaccerd.utils.TickerMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by allan on 30/12/17.
 */
class MainActivityViewModel  @Inject constructor(val interactor: TickerGetterInteractor) : ViewModel() {
    private var ticker: MutableLiveData<List<Ticker>>? = null

    fun getTicker(): LiveData<List<Ticker>> {
        if (ticker == null) {
            ticker = MutableLiveData()

            interactor.setFiat("CAD")
            loadTicker(interactor.execute())
        }
        return ticker!!
    }

    private fun loadTicker(single: Single<List<CoinMarketCapApi.Ticker>>?) {
        if (single != null) {
            single.observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        ticker?.postValue(t.map { TickerMapper.map(it)})
                    }, { error ->
                        Timber.e(error)
                    })
        }
    }
}