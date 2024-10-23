package com.gulsengunes.wordmonster.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.gulsengunes.wordmonster.data.model.Word

class WordRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getWords(onResult: (List<Word>) -> Unit) {
        firestore.collection("words").get().addOnSuccessListener { documents ->
            val wordList = documents.map { doc ->
                Word(
                    id = doc.id,
                    word = doc.getString("word") ?: "",
                    meaning = doc.getString("meaning") ?: "",
                    favorite = doc.getBoolean("favorite") ?: false
                )
            }
            onResult(wordList)
        }.addOnFailureListener { exception ->
            onResult(emptyList())
        }
    }
    fun getMeaningForWord(word: String, onResult: (String?) -> Unit) {
        firestore.collection("words")
            .whereEqualTo("word", word)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val meaning = documents.first().getString("meaning")
                    onResult(meaning)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onResult(null)
            }
    }
}