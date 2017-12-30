package com.themegalith.bks.yaccerd.repository

import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by allan on 28/12/17.
 */
class Repository @Inject constructor(val service: CoinMarketCapApi.Service) {
    fun getTickerForSpecificCrypto(id: String, fiat: String?) : Single<CoinMarketCapApi.Ticker>? {
        return service.getTickerForSpecificCrypto(id, fiat)
                .subscribeOn(Schedulers.io())
                .map { result ->

                    when {
                        result.isError -> {Timber.e(result.error())
                            throw result.error()!!}
                        !(result.response()?.isSuccessful)!! -> throw RuntimeException(result.response()?.errorBody().toString())
                        else -> return@map result.response()?.body()?.get(0)
                    }
                }
    }
}