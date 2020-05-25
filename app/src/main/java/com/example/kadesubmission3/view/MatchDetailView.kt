package com.example.kadesubmission3.view

import com.example.kadesubmission3.model.MatchDetail
import com.example.kadesubmission3.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: MatchDetail)
    fun showTeamBadge(team: Team, isHomeTeam: Boolean)
}