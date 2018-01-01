package com.themegalith.bks.yaccerd

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabItem
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent
import com.themegalith.bks.yaccerd.di.module.MainModule
import com.themegalith.bks.yaccerd.presentation.BaseActivity
import com.themegalith.bks.yaccerd.presentation.adapter.TickerAdapter
import com.themegalith.bks.yaccerd.viewModel.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel
    var tickerAdapter: TickerAdapter? = null

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


        viewModel.getTicker().observe(this,
                Observer {
                    if (tickerAdapter == null ){
                        tickerAdapter = TickerAdapter(this)
                        recyclerview.adapter = tickerAdapter
                    }
                    if (it != null){
                        tickerAdapter?.tickers = it
                        tickerAdapter?.filter?.filter("")
                    }
                })


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return consume { menuInflater.inflate(R.menu.menu_main, menu) }
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


    }
