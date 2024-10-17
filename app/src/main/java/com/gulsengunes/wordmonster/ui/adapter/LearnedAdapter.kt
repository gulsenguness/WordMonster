package com.gulsengunes.wordmonster.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word

class LearnedAdapter(private var learnedWords: List<Word>) :
    RecyclerView.Adapter<LearnedAdapter.LearnedViewHolder>() {

    inner class LearnedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTitle: TextView = itemView.findViewById(R.id.twWordTitle)
        val wordMeaning: TextView = itemView.findViewById(R.id.twWordMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.learned_word_item, parent, false)
        return LearnedViewHolder(view)
    }

    override fun getItemCount(): Int = learnedWords.size

    override fun onBindViewHolder(holder: LearnedViewHolder, position: Int) {
        val word = learnedWords[position]
        holder.wordTitle.text = word.word
        holder.wordMeaning.text = word.meaning
        // Burada kelimeyi kaldırma butonu için tıklama dinleyicisi ekleyebilirsiniz

    }

    fun updateLearnedWords(newLearnedWords: List<Word>) {
        learnedWords = newLearnedWords
        notifyDataSetChanged()
    }
}