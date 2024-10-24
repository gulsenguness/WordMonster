package com.gulsengunes.wordmonster.ui.adapter

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.FavoriteRepository
import com.gulsengunes.wordmonster.data.repository.LearnedRepository

class WordAdapter(
    private var wordList: List<Word>,
    private val learnedRepository: LearnedRepository,
    private val favoriteRepository: FavoriteRepository,
    private val tts: TextToSpeech
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTitle: TextView = itemView.findViewById(R.id.twWordTitle)
        val wordMeaning: TextView = itemView.findViewById(R.id.twWordMeaning)
        val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        val learnedButton: Button = itemView.findViewById(R.id.btnLearned)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val ivListen: ImageView = itemView.findViewById(R.id.ivlisten)


        init {
            ivListen.setOnClickListener {
                val word = wordList[adapterPosition]
                tts.speak(word.word, TextToSpeech.QUEUE_FLUSH, null, null)
            }
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
            ivFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = wordList[position]
                    word.favorite = !word.favorite
                    if (word.favorite) {
                        favoriteRepository.addFavoriteWord(word.word)
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    } else {
                        favoriteRepository.removeFavoriteWord(word.word)
                        ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                    }
                    notifyItemChanged(position)
                }
            }
            ivDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = wordList[position]

                    FirebaseFirestore.getInstance().collection("words")
                        .document(word.id)
                        .delete()
                        .addOnSuccessListener {
                            val updatedWordList = wordList.toMutableList()
                            updatedWordList.removeAt(position)
                            wordList = updatedWordList
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, wordList.size)
                        }
                        .addOnFailureListener { e ->
                            Log.e("DeleteError", "Kelime silinirken hata oluştu", e)
                        }
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