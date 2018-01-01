package com.themegalith.bks.yaccerd.presentation.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.themegalith.bks.yaccerd.R
import com.themegalith.bks.yaccerd.presentation.model.Ticker
import kotlinx.android.synthetic.main.cardview_ticker.view.*
import timber.log.Timber

/**
 * Created by allan on 31/12/17.
 */
class TickerAdapter(context: Context) : RecyclerView.Adapter<TickerAdapter.TickerViewHolder>(), Filterable{

    var tickers: List<Ticker> = mutableListOf()
    var filteredTickers : List<Ticker> = mutableListOf()

    override fun onBindViewHolder(holder: TickerViewHolder?, position: Int) {
        holder?.cryptoNameTextView?.setText(filteredTickers[position].name)
        holder?.cryptoPriceTextView?.setText(filteredTickers[position].price.toString())
        holder?.cryptoSymbolTextView?.setText(filteredTickers[position].symbol)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TickerViewHolder {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTickers.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var constraint = constraint.toString()
                var test =  FilterResults().also {
                    var filtered = if (!constraint.isNullOrEmpty()) tickers.filter {
                        Timber.d(it.name)
                        it.name.toLowerCase().contains(constraint, true)
                    }
                        else tickers
                    it.values = filtered
                    it.count = filtered.size
                }
                return test
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTickers = results?.values as List<Ticker>
                notifyDataSetChanged()
            }

        }
    }

    inner class TickerViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        var cryptoSymbolTextView: TextView = itemView.cryptoSymbol as TextView
        var cryptoNameTextView: TextView = itemView.cryptoName as TextView
        var cryptoPriceTextView: TextView = itemView.cryptoPrice as TextView
    }
}