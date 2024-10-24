package com.gulsengunes.wordmonster.ui.adapter

import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.LearnedRepository

class LearnedAdapter(
    private var learnedWords: List<Word>,
    private val learnedRepository: LearnedRepository,
    private val onUnlearned: (Word) -> Unit,
    private val tts: TextToSpeech

) :
    RecyclerView.Adapter<LearnedAdapter.LearnedViewHolder>() {

    inner class LearnedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTitle: TextView = itemView.findViewById(R.id.twWordTitle)
        val wordMeaning: TextView = itemView.findViewById(R.id.twWordMeaning)
        val unlearnedButton: Button = itemView.findViewById(R.id.unlearned)
        val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val ivListen: ImageView = itemView.findViewById(R.id.ivlisten)


        init {
            ivListen.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = learnedWords[position]
                    tts.speak(word.word, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
            unlearnedButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = learnedWords[position]
                    onUnlearned(word)
                }
            }

            ivDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val word = learnedWords[position]


                    FirebaseFirestore.getInstance().collection("words")
                        .document(word.id)
                        .delete()
                        .addOnSuccessListener {
                            learnedRepository.removeLearnedWord(word.word)
                            val updatedWordList = learnedWords.toMutableList()
                            updatedWordList.removeAt(position)
                            learnedWords = updatedWordList
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, learnedWords.size)
                        }
                        .addOnFailureListener { e ->
                            Log.e("DeleteError", "Kelime silinirken hata oluştu", e)
                        }
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

    fun updateLearnedWords(newLearnedWords: List<Word>) {
        learnedWords = newLearnedWords
        notifyDataSetChanged()
    }
}