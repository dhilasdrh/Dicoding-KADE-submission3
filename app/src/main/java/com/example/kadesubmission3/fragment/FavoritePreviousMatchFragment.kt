package com.example.kadesubmission3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kadesubmission3.R
import com.example.kadesubmission3.activity.MatchDetailActivity
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_AWAY_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_HOME_TEAM
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.ID_MATCH
import com.example.kadesubmission3.activity.MatchDetailActivity.Companion.MATCH_STATUS
import com.example.kadesubmission3.adapter.FavoriteMatchAdapter
import com.example.kadesubmission3.database.database
import com.example.kadesubmission3.invisible
import com.example.kadesubmission3.database.Favorite
import com.example.kadesubmission3.visible
import kotlinx.android.synthetic.main.fragment_fav_previous_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavoritePreviousMatchFragment : Fragment(){
    private var favorites: MutableList<Favorite> = mutableListOf()
    private var isLastMatch: Boolean = true
    private lateinit var adapter: FavoriteMatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val actionBar: ActionBar? = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return inflater.inflate(R.layout.fragment_fav_previous_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoriteMatchAdapter(favorites){
            startActivity<MatchDetailActivity>(
                ID_MATCH to it.idMatch,
                ID_HOME_TEAM to it.idHomeTeam,
                ID_AWAY_TEAM to it.idAwayTeam,
                MATCH_STATUS to isLastMatch)
        }
        rv_favLastMatch.layoutManager = LinearLayoutManager(context)
        rv_favLastMatch.adapter = adapter

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(STATUS = {status})",
                "status" to isLastMatch)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

        if (favorites.isNullOrEmpty()) {
            img_noData.visible()
            tv_noData.visible()
        } else {
            img_noData.invisible()
            tv_noData.invisible()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}
