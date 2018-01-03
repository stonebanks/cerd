package com.themegalith.bks.cerd.domain.interactor

import com.themegalith.bks.cerd.network.CoinMarketCapApi
import com.themegalith.bks.cerd.repository.Repository
import io.reactivex.Single

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

    fun execute() : Single<MutableList<CoinMarketCapApi.Ticker>>? {
        return repository.getTickerForSpecificCrypto(fiat)
    }
}