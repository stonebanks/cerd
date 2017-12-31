package com.themegalith.bks.yaccerd.utils

import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import com.themegalith.bks.yaccerd.presentation.model.Ticker

/**
 * Created by allan on 31/12/17.
 */
class TickerMapper {
    companion object {
        fun map(ticker: CoinMarketCapApi.Ticker) : Ticker = Ticker(
                name = ticker.name!!,
                symbol = ticker.symbol!!,
                price = ticker.price_usd,
                percentChange24h = ticker.percent_change_24h)
    }
}