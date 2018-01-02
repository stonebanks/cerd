package com.themegalith.bks.yaccerd.domain.interactor

import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import com.themegalith.bks.yaccerd.repository.Repository
import io.reactivex.Single

import timber.log.Timber
import javax.inject.Inject

/**
 * Created by allan on 30/12/17.
 */
class TickerGetterInteractor @Inject constructor(val repository: Repository) {

    private var cryptoId : String? = null
    private var fiat: String? = null

    fun setFiat(value : String){
        fiat = value
    }

    fun execute() : Single<List<CoinMarketCapApi.Ticker>>? {
        return repository.getTickerForSpecificCrypto(fiat)
    }
}