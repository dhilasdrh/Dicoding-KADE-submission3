package com.example.kadesubmission3.presenter

import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.api.TheSportDBApi
import com.example.kadesubmission3.model.MatchResponse
import com.example.kadesubmission3.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getPreviousMatch(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getPastMatch(idLeague)).await(),
                MatchResponse::class.java
            )

            view.showMatchList(data.events)
            view.hideLoading()
        }
    }

    fun getNextMatch(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getNextMatch(idLeague)).await(),
                MatchResponse::class.java
            )

            view.showMatchList(data.events)
            view.hideLoading()
        }
    }
}