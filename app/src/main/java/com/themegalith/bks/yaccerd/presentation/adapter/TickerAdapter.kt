package com.themegalith.bks.yaccerd.presentation.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.themegalith.bks.yaccerd.R
import com.themegalith.bks.yaccerd.presentation.model.Ticker

/**
 * Created by allan on 31/12/17.
 */
class TickerAdapter(tickers: List<Ticker>) : RecyclerView.Adapter<TickerAdapter.TickerViewHolder>() {

    private var tickers: List<Ticker> = mutableListOf()

    init {
        this.tickers = tickers
    }

    override fun onBindViewHolder(holder: TickerViewHolder?, position: Int) {
        holder?.cryptoNameTextView?.setText(tickers[position].name)
        holder?.cryptoPriceTextView?.setText(tickers[position].price.toString())
        holder?.cryptoSymbolTextView?.setText(tickers[position].symbol)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TickerViewHolder {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tickers.size
    }

    inner class TickerViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        var cryptoSymbolTextView: TextView = itemView.findViewById(R.id.crypto_symbol) as TextView
        var cryptoNameTextView: TextView = itemView.findViewById(R.id.crypto_name) as TextView
        var cryptoPriceTextView: TextView = itemView.findViewById(R.id.crypto_price) as TextView
        var cardView: CardView =  itemView.findViewById(R.id.card_view) as CardView
    }
}