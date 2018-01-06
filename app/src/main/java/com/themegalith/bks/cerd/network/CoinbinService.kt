package com.themegalith.bks.cerd.network

import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by allan on 05/01/18.
 */
object CoinbinApi {
    class Response {
        var coins : MutableMap<String, CoinbinApi.Ticker> = mutableMapOf()
    }

    data class Ticker(val btc: Double, val name: String, val rank: Int, val ticker: String, val usd: Double)

    interface Service {
        @GET
        fun getCoins(@Url url: String = "https://coinbin.org/coins") : Single<Result<Response>>
    }
}