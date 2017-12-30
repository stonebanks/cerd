package com.themegalith.bks.yaccerd.network

import com.squareup.moshi.Json
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by allan on 27/12/17.
 */
object CoinMarketCapApi {

    class Ticker {
        var id: String? = null
        var name: String? = null
        var symbol: String? = null
        var rank: Int = 0
        var price_usd : Double = 0.0
        var price_btc: Double = 0.0
        @Json(name = "24h_volume_usd") var twentyfour_hours_volume_usd: Double = 0.0
        var market_cap_usd: Double = 0.0
        var available_supply: Double = 0.0
        var total_supply: Double = 0.0
        var max_supply: Float? = 0.0f
        var percent_change_1h: Double = 0.0
        var percent_change_24h: Double = 0.0
        var percent_change_7d: Double = 0.0
        var last_updated: Long = 0
    }

    interface Service {
        @GET("ticker/{id}")
        fun getTickerForSpecificCrypto(
                @Path("id") id: String,
                @Query("convert") fiat: String?
        ) : Single<Result<List<Ticker>>>
    }

}