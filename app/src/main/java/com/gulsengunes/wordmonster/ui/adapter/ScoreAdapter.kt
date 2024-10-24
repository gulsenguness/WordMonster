package com.gulsengunes.wordmonster.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulsengunes.wordmonster.R

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    private var scoreList: List<Int> = emptyList()

    fun updateScores(newScores: List<Int>) {
        this.scoreList = newScores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scoreList[position]
        holder.scoreTextView.text = score.toString()
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val scoreTextView: TextView = itemView.findViewById(R.id.score)
    }
}