package com.example.noteapp.ui.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.noteapp.databinding.FragmentBoardBinding
import com.example.noteapp.models.Board
import com.example.noteapp.ui.Prefs

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding

    private val data = arrayListOf<Board>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ololo", "onViewCreated prefs: ${Prefs(requireContext()).isShown()}", )
        loadData()
        val adapter = BoardAdapter(requireContext(), findNavController(), data)
        binding.viewPager.adapter = adapter

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }

        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.textSkip.setOnClickListener {
            //Prefs(requireContext()).saveState()
            Log.e("ololo", "onViewCreated prefs: ${Prefs(requireContext()).isShown()}", )
            findNavController().navigateUp()
        }
    }

    private fun loadData() {
        data.add(
            Board(
                "page1_animation.lottie",
                "Салам"
            )
        )
        data.add(
            Board(
                "page2_animation.json",
                "Привет"
            )
        )
        data.add(
            Board(
                "page3_animation.json",
                "Hello"
            )
        )
    }

}