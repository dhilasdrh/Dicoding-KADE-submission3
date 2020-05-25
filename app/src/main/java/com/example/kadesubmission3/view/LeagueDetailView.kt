package com.example.kadesubmission3.view

import com.example.kadesubmission3.model.LeagueDetail

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: LeagueDetail)
}