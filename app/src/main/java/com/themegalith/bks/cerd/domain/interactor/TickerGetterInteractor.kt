package com.themegalith.bks.cerd.domain.interactor

import com.themegalith.bks.cerd.network.CoinbinApi
import com.themegalith.bks.cerd.repository.CoinbinRepository
import io.reactivex.Single

import javax.inject.Inject

/**
 * Created by allan on 30/12/17.
 */
class TickerGetterInteractor @Inject constructor(val repository: CoinbinRepository) {

    fun execute() : Single<List<CoinbinApi.Ticker>>{
        return repository.getTickers()
    }

}