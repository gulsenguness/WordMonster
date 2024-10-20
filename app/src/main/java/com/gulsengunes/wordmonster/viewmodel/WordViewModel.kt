package com.gulsengunes.wordmonster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gulsengunes.wordmonster.data.model.Word

class WordViewModel : ViewModel() {
    private val _wordList = MutableLiveData<List<Word>>(listOf())
    val wordList: LiveData<List<Word>> get() = _wordList

    fun addWord(word: Word ) {
        _wordList.value = _wordList.value?.toMutableList()?.apply { add(word) }
    }

    fun loadWords(words: List<Word>) {
        _wordList.value = words
    }
}