package com.themegalith.bks.cerd.utils

import com.themegalith.bks.cerd.network.CoinbinApi
import com.themegalith.bks.cerd.presentation.model.TickerModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Created by allan on 31/12/17.
 */
class TickerMapper {
    companion object {
        fun convert(ticker: CoinbinApi.Ticker) : TickerModel = TickerModel(
                name = ticker.name,
                symbol = ticker.ticker.toUpperCase(),
                price = Currency.getInstance("USD").symbol.toString() +
                        BigDecimal(ticker.usd).setScale(Currency.getInstance("USD").defaultFractionDigits, RoundingMode.HALF_UP),
                rank = ticker.rank)
    }
}
