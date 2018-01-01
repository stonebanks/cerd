package com.themegalith.bks.yaccerd

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent
import com.themegalith.bks.yaccerd.di.module.MainModule
import com.themegalith.bks.yaccerd.presentation.BaseActivity
import com.themegalith.bks.yaccerd.presentation.adapter.TickerAdapter
import com.themegalith.bks.yaccerd.presentation.model.Ticker
import com.themegalith.bks.yaccerd.viewModel.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import org.reactivestreams.Subscription
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel
    lateinit var recyclerView : RecyclerView
    var tickerAdapter: TickerAdapter? = null

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(MainModule(this))
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.my_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)



        viewModel.getTicker().observe(this,
                Observer {
                    if (tickerAdapter == null ){
                        tickerAdapter = TickerAdapter(this)
                        recyclerView.adapter = tickerAdapter
                    }
                    if (it != null){
                        tickerAdapter!!.tickers = it
                    }
                })


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
