package com.example.kadesubmission3.view

import com.example.kadesubmission3.model.Match

interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchSearchResult(data: List<Match>)
    fun showEmpty()
}