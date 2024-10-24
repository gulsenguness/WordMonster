package com.gulsengunes.wordmonster.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel:ViewModel() {
    private val _scoreList = MutableLiveData<List<Int>>(emptyList())
    val scoreList: LiveData<List<Int>> get() = _scoreList

    fun updateScore(newScore: Int) {
        _scoreList.value= listOf(newScore)
    }

}