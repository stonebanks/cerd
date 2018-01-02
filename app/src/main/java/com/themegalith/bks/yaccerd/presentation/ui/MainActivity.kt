package com.themegalith.bks.yaccerd

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent
import com.themegalith.bks.yaccerd.di.module.MainModule
import com.themegalith.bks.yaccerd.presentation.BaseActivity
import com.themegalith.bks.yaccerd.presentation.adapter.TickerAdapter
import com.themegalith.bks.yaccerd.presentation.model.TickerModel
import com.themegalith.bks.yaccerd.viewModel.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel
    private var tickerAdapter: TickerAdapter? = null
    private lateinit var observer: Observer<MutableList<TickerModel>>
    private lateinit var subscription: Disposable

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(MainModule(this))
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        observer = Observer {
            if (tickerAdapter == null) {
                tickerAdapter = TickerAdapter(this)
                recyclerview.adapter = tickerAdapter
            }

            tickerAdapter?.tickers = it!!
            tickerAdapter?.filter?.filter("")

            if (swipeRefreshLayout.isRefreshing) swipeRefreshLayout.isRefreshing = false
        }

        viewModel.getLoadingStatus().observe(this, Observer { showLoadingWheel(it != null && it) })

        viewModel.getTicker().observe(this, observer)

        swipeRefreshLayout.setColorSchemeColors(, R.color.green, R.color.red, R.color.yellow)
        subscription = RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.disposeSub()
                    tickerAdapter?.clear()
                    viewModel.getTicker().observe(this, observer)
                }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return consume { menuInflater.inflate(R.menu.menu_main, menu) }
    }

    private fun showLoadingWheel(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_search -> consume {
                    RxSearchView.queryTextChanges(item.actionView as SearchView)
                            .debounce(500, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                tickerAdapter?.filter?.filter(it)
                            }
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onDestroy() {
        super.onDestroy()
        if (!subscription.isDisposed) subscription.dispose()
    }
}
