package com.example.kadesubmission3.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kadesubmission3.activity.MatchDetailActivity
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_AWAY_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_HOME_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_MATCH
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.MATCH_STATUS
import com.example.kadesubmission3.adapter.MatchAdapter
import com.example.kadesubmission3.api.ApiRepository
import com.example.kadesubmission3.invisible
import com.example.kadesubmission3.model.Match
import com.example.kadesubmission3.presenter.MatchPresenter
import com.example.kadesubmission3.view.MatchView
import com.example.kadesubmission3.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.startActivity

class PreviousMatchFragment(private val idLeague: String?) : Fragment(), MatchView, AnkoComponent<Context> {
    private var match: MutableList<Match> = mutableListOf()
    private val isLastMatch: Boolean = true
    private lateinit var rvLastMatch: RecyclerView
    private lateinit var pbLastMatch: ProgressBar
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = MatchPresenter(this, ApiRepository(), Gson())

        val actionBar: ActionBar? = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getPreviousMatch(idLeague)
        adapter = MatchAdapter(match) {
            startActivity<MatchDetailActivity>(
                ID_MATCH to it.idEvent,
                ID_HOME_TEAM to it.idHomeTeam,
                ID_AWAY_TEAM to it.idAwayTeam,
                MATCH_STATUS to isLastMatch
            )
        }
        rvLastMatch.layoutManager = LinearLayoutManager(context)
        rvLastMatch.adapter = adapter
    }

    override fun showLoading() {
        pbLastMatch.visible()
    }

    override fun hideLoading() {
        pbLastMatch.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            rvLastMatch = recyclerView {
                lparams(matchParent, wrapContent)
            }

            pbLastMatch = progressBar {
            }.lparams(matchParent, wrapContent) {
                centerInParent()
            }
        }
    }
}