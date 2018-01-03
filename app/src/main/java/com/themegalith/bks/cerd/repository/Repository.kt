package com.themegalith.bks.cerd.repository

import com.themegalith.bks.cerd.network.CoinMarketCapApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by allan on 28/12/17.
 */
class Repository @Inject constructor(val service: CoinMarketCapApi.Service) {
    fun getTickerForSpecificCrypto(fiat: String?) : Single<MutableList<CoinMarketCapApi.Ticker>>? {
        return service.getTickerForSpecificCrypto(fiat)
                .subscribeOn(Schedulers.io())
                .map { result ->

                    when {
                        result.isError -> {Timber.e(result.error())
                            throw result.error()!!}
                        !(result.response()?.isSuccessful)!! -> throw RuntimeException(result.response()?.errorBody().toString())
                        else -> return@map result.response()?.body()
                    }
                }
    }
}