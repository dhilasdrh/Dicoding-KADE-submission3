package com.example.kadesubmission3.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kadesubmission3.R
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_AWAY_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_HOME_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_MATCH
import com.example.kadesubmission3.adapter.MatchAdapter
import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.invisible
import com.example.kadesubmission3.model.Match
import com.example.kadesubmission3.presenter.MatchSearchPresenter
import com.example.kadesubmission3.view.MatchSearchView
import com.example.kadesubmission3.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match_search.*
import org.jetbrains.anko.startActivity

class MatchSearchActivity : AppCompatActivity(), MatchSearchView, MenuItem.OnActionExpandListener {
    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchSearchPresenter
    private lateinit var adapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        presenter = MatchSearchPresenter(this, ApiRepository(), Gson())
        adapter = MatchAdapter(match) {
            startActivity<MatchDetailActivity>(
                ID_MATCH to it.idEvent,
                ID_HOME_TEAM to it.idHomeTeam,
                ID_AWAY_TEAM to it.idAwayTeam
            )
        }
        rv_matchSearch.layoutManager = LinearLayoutManager(this)
        rv_matchSearch.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.searchView)
        val searchView = searchItem?.actionView as SearchView
        searchItem.expandActionView()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getMatchSearchResult(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 1) {
                    presenter.getMatchSearchResult(newText)
                }
                return true
            }
        })
        searchItem.setOnActionExpandListener(this)
        return true
    }

    override fun showLoading() {
        pbSearch.visible()
    }

    override fun hideLoading() {
        pbSearch.invisible()
    }

    override fun showMatchSearchResult(data: List<Match>) {
        match.clear()
        data.forEach {
            if (it.sport.equals("Soccer")) {
                match.add(it)
            }
        }
        adapter.notifyDataSetChanged()
        imgEmpty.invisible()
        tvEmpty.invisible()
    }

    override fun showEmpty() {
        match.clear()
        adapter.notifyDataSetChanged()
        imgEmpty.visible()
        tvEmpty.visible()
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }
}