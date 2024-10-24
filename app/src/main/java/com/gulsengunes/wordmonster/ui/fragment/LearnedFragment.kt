package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.LearnedRepository
import com.gulsengunes.wordmonster.data.repository.WordRepository
import com.gulsengunes.wordmonster.databinding.FragmentLearnedBinding
import com.gulsengunes.wordmonster.ui.adapter.LearnedAdapter
import java.util.Locale


class LearnedFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentLearnedBinding
    private lateinit var learnedRepository: LearnedRepository
    private lateinit var learnedAdapter: LearnedAdapter
    private lateinit var wordRepository: WordRepository
    private var learnedWords: Set<String> = emptySet()
    private lateinit var tts: TextToSpeech


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        learnedRepository = LearnedRepository(requireContext())
        wordRepository = WordRepository()
        tts = TextToSpeech(context, this)
        setupRecyclerView()
        loadLearnedWords()
    }

    private fun setupRecyclerView() {
        learnedAdapter = LearnedAdapter(emptyList(), learnedRepository, { word ->
            learnedRepository.removeLearnedWord(word.word)
            loadLearnedWords()
        }, tts)
        binding.recyclerLearned.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerLearned.adapter = learnedAdapter
    }

    private fun loadLearnedWords() {
        learnedWords = learnedRepository.getAllLearnedWords()
        val wordList = mutableListOf<Word>()

        for (wordString in learnedWords) {
            wordRepository.getMeaningForWord(wordString) { meaning ->
                val word = Word(
                    id = "",
                    word = wordString.trim(),
                    meaning = meaning ?: "Anlam Bulunamadı",
                    favorite = false
                )
                wordList.add(word)

                if (wordList.size == learnedWords.size) {
                    learnedAdapter.updateLearnedWords(wordList)
                }
            }
        }

        wordRepository.getWords { words ->
            words.forEach { word ->
                if (learnedWords.contains(word.word)) {
                    val index = wordList.indexOfFirst { it.word == word.word }
                    if (index != -1) {
                        wordList[index] = word.copy()
                    }
                }
            }
            learnedAdapter.updateLearnedWords(wordList)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Dil desteklenmiyor.")
            }
        } else {
            Log.e("TTS", "Text-to-Speech başlatılamadı.")
        }
    }

}