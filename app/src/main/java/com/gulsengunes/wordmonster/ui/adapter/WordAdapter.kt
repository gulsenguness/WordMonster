package com.gulsengunes.wordmonster.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.LearnedRepository

class WordAdapter(
    private var wordList: List<Word>,
    private val learnedRepository: LearnedRepository
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTitle: TextView = itemView.findViewById(R.id.twWordTitle)
        val wordMeaning: TextView = itemView.findViewById(R.id.twWordMeaning)
        val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        val learnedButton: Button = itemView.findViewById(R.id.btnLearned)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)


        init {
            learnedButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = wordList[position]
                    learnedRepository.addLearnedWord(word.word)
                    val updatedWordList = wordList.toMutableList()
                    updatedWordList.removeAt(position)
                    wordList = updatedWordList
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, wordList.size)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.wordTitle.text = word.word
        holder.wordMeaning.text = word.meaning

        if (word.favorite) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

        holder.ivFavorite.setOnClickListener {
            word.favorite = !word.favorite
            notifyItemChanged(position)
        }



    }

    fun updateWordList(newWordList: List<Word>) {
        wordList = newWordList
        notifyDataSetChanged()
    }
}