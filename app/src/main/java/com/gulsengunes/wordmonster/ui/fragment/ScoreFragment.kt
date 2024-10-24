package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulsengunes.wordmonster.databinding.FragmentScoreBinding
import com.gulsengunes.wordmonster.ui.adapter.ScoreAdapter
import com.gulsengunes.wordmonster.viewmodel.ScoreViewModel

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private lateinit var scoreAdapter: ScoreAdapter
    private val scoreViewModel: ScoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        scoreAdapter = ScoreAdapter()
        binding.recyclerView.adapter = scoreAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        scoreViewModel.scoreList.observe(viewLifecycleOwner) { scores ->
            scoreAdapter.updateScores(scores)
        }
        return binding.root
    }

}