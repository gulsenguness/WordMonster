package com.gulsengunes.wordmonster.data.repository

import android.content.SharedPreferences
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class LearnedRepository(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("learned_words", Context.MODE_PRIVATE)

    fun addLearnedWord(word: String) {
        val learnedWords = getAllLearnedWords().toMutableSet()
        learnedWords.add(word)
        sharedPreferences.edit().putStringSet("learned_words", learnedWords).apply()
    }

    fun removeLearnedWord(word: String) {
        val learnedWords = getAllLearnedWords().toMutableSet()
        learnedWords.remove(word)
        sharedPreferences.edit().putStringSet("learned_words", learnedWords).apply()
    }

    fun getAllLearnedWords(): Set<String> {
        return sharedPreferences.getStringSet("learned_words", emptySet()) ?: emptySet()
    }
}