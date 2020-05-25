package com.example.kadesubmission3.presenter

import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.api.TheSportDBApi
import com.example.kadesubmission3.model.MatchSearchResponse
import com.example.kadesubmission3.view.MatchSearchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchSearchPresenter(private val view: MatchSearchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson
){
    fun getMatchSearchResult(query: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getMatchSearch(query)).await(),
                MatchSearchResponse::class.java)

            if (data.event.isNullOrEmpty())
                view.showEmpty()
            else
                view.showMatchSearchResult(data.event)

            view.hideLoading()
        }
    }
}