package com.gulsengunes.wordmonster.data.repository

import android.content.SharedPreferences
import android.content.Context

class FavoriteRepository(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorite_words", Context.MODE_PRIVATE)
    private val FAVORITES_KEY = "favorites"

    fun addFavoriteWord(word: String) {
        val favorites = getAllFavoriteWords().toMutableSet()
        favorites.add(word)
        sharedPreferences.edit().putStringSet(FAVORITES_KEY, favorites).apply()
    }

    fun removeFavoriteWord(word: String) {
        val favoriteWords = getAllFavoriteWords().toMutableSet()
        if (favoriteWords.remove(word)) {
            sharedPreferences.edit().putStringSet(FAVORITES_KEY, favoriteWords).apply()
        }
    }

    fun getAllFavoriteWords(): Set<String> {
        return sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }
}