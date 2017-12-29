package com.themegalith.bks.yaccerd.repository

import com.themegalith.bks.yaccerd.network.services.CoinMarketCapApi
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava.Result
import javax.inject.Inject

/**
 * Created by allan on 28/12/17.
 */
class Repository @Inject constructor(val service: CoinMarketCapApi.Service) {
    suspend fun getTickerForSpecificCrypto(id: String, fiat: String?) : Single<List<CoinMarketCapApi.Ticker>>{
        return service.getTickerForSpecificCrypto(id, fiat)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.isError) throw it.error()!!
                    return@map it.response()?.body()
                }
    }
}