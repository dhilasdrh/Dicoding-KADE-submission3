package com.example.kadesubmission3.presenter

import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.api.TheSportDBApi
import com.example.kadesubmission3.model.LeagueDetailResponse
import com.example.kadesubmission3.view.LeagueDetailView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter (private val view: LeagueDetailView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson)
{
    fun getLeagueDetail(idLeague: String){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getLeagueDetail(idLeague)).await(),
                LeagueDetailResponse::class.java)

            view.showLeagueDetail(data.leagues[0])
            view.hideLoading()
        }
    }
}