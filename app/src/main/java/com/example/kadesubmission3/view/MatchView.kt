package com.example.kadesubmission3.view

import com.example.kadesubmission3.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}