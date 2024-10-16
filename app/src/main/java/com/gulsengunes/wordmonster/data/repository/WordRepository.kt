package com.gulsengunes.wordmonster.data.repository

import com.google.firebase.firestore.FirebaseFirestore

import com.gulsengunes.wordmonster.data.model.Word

class WordRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getWords(onResult: (List<Word>) -> Unit) {
        firestore.collection("words").get().addOnSuccessListener { documents ->
            val wordList = documents.map { doc ->
                doc.toObject(Word::class.java)
            }
            onResult(wordList)
        }
            .addOnFailureListener { exception ->
                onResult(emptyList())
            }
    }
}