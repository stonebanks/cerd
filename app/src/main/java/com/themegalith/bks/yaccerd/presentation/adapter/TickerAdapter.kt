package com.themegalith.bks.yaccerd.presentation.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import com.themegalith.bks.yaccerd.R
import com.themegalith.bks.yaccerd.presentation.model.TickerModel
import kotlinx.android.synthetic.main.cardview_ticker.view.*
import timber.log.Timber

/**
 * Created by allan on 31/12/17.
 */
class TickerAdapter(context: Context) : RecyclerView.Adapter<TickerAdapter.TickerViewHolder>(), FastScrollRecyclerView.SectionedAdapter, Filterable{
    override fun getSectionName(position: Int): String {
        return Character.toString(filteredTickers.get(position).symbol[0])
    }

    var tickers: MutableList<TickerModel> = mutableListOf()
    var filteredTickers : MutableList<TickerModel> = mutableListOf()

    override fun onBindViewHolder(holder: TickerViewHolder?, position: Int) {
        holder?.cryptoNameTextView?.text = filteredTickers[position].name
        holder?.cryptoPriceTextView?.text = filteredTickers[position].price
        holder?.cryptoSymbolTextView?.text = filteredTickers[position].symbol
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TickerViewHolder {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTickers.size
    }

    fun clear() {
        filteredTickers = mutableListOf()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var constraint = constraint.toString()
                var test =  FilterResults().also {
                    var filtered = if (!constraint.isNullOrEmpty()) tickers.filter {
                        it.name.contains(constraint, true) || it.symbol.contains(constraint, true)
                    }
                        else tickers
                    it.values = filtered
                    it.count = filtered.size
                }
                return test
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Timber.d("Publishing filtered results...")
                filteredTickers = results?.values as MutableList<TickerModel>
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