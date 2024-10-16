package com.gulsengunes.wordmonster.data.model

data class Word(
    val id: String = "",
    val word: String = "",
    val meaning: String = "",
    var favorite: Boolean = false
)
