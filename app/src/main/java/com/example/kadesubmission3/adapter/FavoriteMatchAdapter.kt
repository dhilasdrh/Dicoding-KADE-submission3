package com.example.kadesubmission3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kadesubmission3.R
import com.example.kadesubmission3.database.Favorite
import com.example.kadesubmission3.formatDate
import com.example.kadesubmission3.formatTime

class FavoriteMatchAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val matchDate: TextView = view.findViewById(R.id.match_date)
        private val matchTime: TextView = view.findViewById(R.id.match_time)
        private val homeTeamName: TextView = view.findViewById(R.id.home_team)
        private val homeTeamScore: TextView = view.findViewById(R.id.home_score)
        private val awayTeamName: TextView = view.findViewById(R.id.away_team)
        private val awayTeamScore: TextView = view.findViewById(R.id.away_score)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
            if (favorite.matchDate.isNullOrEmpty() or favorite.matchTime.isNullOrEmpty()) {
                matchDate.text = ""
            } else {
                matchDate.text = favorite.matchDate?.formatDate()
                matchTime.text = favorite.matchTime?.formatTime()
            }
            homeTeamName.text = favorite.homeTeamName
            homeTeamScore.text = favorite.homeTeamScore
            awayTeamName.text = favorite.awayTeamName
            awayTeamScore.text = favorite.awayTeamScore

            itemView.setOnClickListener {
                listener(favorite)
            }
        }
    }
}
