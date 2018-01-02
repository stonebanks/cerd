package com.themegalith.bks.yaccerd.utils

import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import com.themegalith.bks.yaccerd.presentation.model.TickerModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Created by allan on 31/12/17.
 */
class TickerMapper {
    companion object {
        fun convert(ticker: CoinMarketCapApi.Ticker) : TickerModel = TickerModel(
                name = ticker.name!!,
                symbol = ticker.symbol!!,
                price = Currency.getInstance("USD").getSymbol().toString() +
                        BigDecimal(ticker.price_usd).setScale(Currency.getInstance("USD").defaultFractionDigits, RoundingMode.HALF_UP),
                percentChange24h = ticker.percent_change_24h)
    }
}
