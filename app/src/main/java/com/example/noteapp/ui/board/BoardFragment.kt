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
import kotlin.math.log

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
            Prefs(requireContext()).saveState()
            Log.e("ololo", "onViewCreated prefs: ${Prefs(requireContext()).isShown()}", )
            findNavController().navigateUp()
        }
    }

    private fun loadData() {
        data.add(
            Board(
                "https://i.pinimg.com/736x/2d/7e/7b/2d7e7bb168993f388ec802c58efc5da8.jpg",
                "Салам"
            )
        )
        data.add(
            Board(
                "https://i.pinimg.com/736x/1e/c3/ab/1ec3ab5a3a6f58f2c4fa7ec58605d56f.jpg",
                "Привет"
            )
        )
        data.add(
            Board(
                "https://i.pinimg.com/736x/29/6d/e9/296de922fffd4b162474f58fec4232a3.jpg",
                "Hello"
            )
        )
    }

}