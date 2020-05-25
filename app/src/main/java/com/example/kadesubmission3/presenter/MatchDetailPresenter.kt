package com.example.kadesubmission3.presenter

import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.api.TheSportDBApi
import com.example.kadesubmission3.model.MatchDetailResponse
import com.example.kadesubmission3.model.TeamResponse
import com.example.kadesubmission3.view.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){
    fun getMatchDetail(idEvent: String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getMatchDetail(idEvent)).await(),
                MatchDetailResponse::class.java)

            view.showMatchDetail(data.events[0])
            view.hideLoading()
        }
    }

    fun getTeamBadge(idTeam: String?, isHomeTeam: Boolean = true) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getTeamDetail(idTeam)).await(),
                TeamResponse::class.java)

            view.showTeamBadge(data.teams[0], isHomeTeam)
            view.hideLoading()
        }
    }
}