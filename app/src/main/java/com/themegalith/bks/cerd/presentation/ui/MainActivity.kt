package com.themegalith.bks.cerd

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.themegalith.bks.cerd.di.component.ApplicationComponent
import com.themegalith.bks.cerd.di.module.MainModule
import com.themegalith.bks.cerd.exception.ApiException
import com.themegalith.bks.cerd.exception.NetworkException
import com.themegalith.bks.cerd.presentation.BaseActivity
import com.themegalith.bks.cerd.presentation.adapter.TickerAdapter
import com.themegalith.bks.cerd.presentation.model.TickerModel
import com.themegalith.bks.cerd.viewModel.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel
    private var tickerAdapter: TickerAdapter = TickerAdapter(this)
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
        recyclerview.adapter = tickerAdapter

        observer = Observer {
            tickerAdapter?.tickers = it!!
            tickerAdapter?.filter?.filter("")

            if (swipeRefreshLayout.isRefreshing) swipeRefreshLayout.isRefreshing = false
        }


        subscription = RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.disposeSub()
                    tickerAdapter?.clear()
                    viewModel.getTicker().observe(this, observer)
                }

        viewModel.getLoadingStatus().observe(this, Observer { showLoadingWheel(it != null && it) })
        viewModel.getThrowable().observe(this, Observer {
            var string = when(it!!){
                is NetworkException -> getString(R.string.network_issue)
                is ApiException -> getString(R.string.apiexection)
                else -> getString(R.string.unknon_issue)
            }
            Snackbar.make(findViewById<ViewGroup>(android.R.id.content), string, Snackbar.LENGTH_LONG).show()
            swipeRefreshLayout.isRefreshing = false
        })
        viewModel.getTicker().observe(this, observer)

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
                R.id.action_about -> consume {
                    var s = SpannableString(Html.fromHtml(getString(R.string.about_message)))
                    Linkify.addLinks(s, Linkify.ALL)

                    AlertDialog.Builder(this).setTitle(R.string.title_about)
                            .setMessage(s).show().findViewById<TextView>(android.R.id.message).movementMethod = LinkMovementMethod.getInstance()
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onDestroy() {
        super.onDestroy()
        if (!subscription.isDisposed) subscription.dispose()
    }
}
