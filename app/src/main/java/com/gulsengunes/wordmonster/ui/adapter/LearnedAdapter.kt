package com.gulsengunes.wordmonster.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.LearnedRepository

class LearnedAdapter(
    private var learnedWords: List<Word>,
    private val learnedRepository: LearnedRepository,
    private val onUnlearned: (Word) -> Unit
) :
    RecyclerView.Adapter<LearnedAdapter.LearnedViewHolder>() {

    inner class LearnedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTitle: TextView = itemView.findViewById(R.id.twWordTitle)
        val wordMeaning: TextView = itemView.findViewById(R.id.twWordMeaning)
        val unlearnedButton: Button = itemView.findViewById(R.id.unlearned)

        init {
            unlearnedButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = learnedWords[position]
                    onUnlearned(word)
                }
            }
        }
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
    }

    fun updateLearnedWords(newLearnedWords: List<Word>) {
        learnedWords = newLearnedWords
        notifyDataSetChanged()
    }
}