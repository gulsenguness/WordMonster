package com.gulsengunes.wordmonster.data.repository

import android.content.SharedPreferences
import android.content.Context

class FavoriteRepository(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorite_words", Context.MODE_PRIVATE)

    fun addFavoriteWord(word: String) {
        val favoriteWords = getAllFavoriteWords().toMutableSet()
        if (favoriteWords.add(word)) { // Eğer kelimeyi eklediyse
            sharedPreferences.edit().putStringSet("favorite_words", favoriteWords).apply()
        }
    }

    fun removeFavoriteWord(word: String) {
        val favoriteWords = getAllFavoriteWords().toMutableSet()
        if (favoriteWords.remove(word)) { // Eğer kelimeyi kaldırdıysa
            sharedPreferences.edit().putStringSet("favorite_words", favoriteWords).apply()
        }
    }

    fun getAllFavoriteWords(): Set<String> {
        return sharedPreferences.getStringSet("favorite_words", emptySet()) ?: emptySet()
    }
}