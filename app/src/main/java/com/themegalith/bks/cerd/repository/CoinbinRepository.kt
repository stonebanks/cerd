package com.themegalith.bks.cerd.repository

import com.themegalith.bks.cerd.exception.ApiException
import com.themegalith.bks.cerd.exception.NetworkException
import com.themegalith.bks.cerd.network.CoinbinApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by allan on 05/01/18.
 */
class CoinbinRepository @Inject constructor(private val service: CoinbinApi.Service){
    fun getTickers() : Single<List<CoinbinApi.Ticker>> {
        return service.getCoins()
                .subscribeOn(Schedulers.io())
                .map { result ->
                    when {
                        result.isError -> throw NetworkException(result.error()!!)
                        !(result.response()?.isSuccessful)!! -> throw ApiException(result.response()?.code()!!, result.response()?.errorBody().toString())
                        else -> return@map result.response()?.body()?.coins?.map { it.value }
                    }
                }
    }
}