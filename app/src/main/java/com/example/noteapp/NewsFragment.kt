package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.databinding.FragmentNewsBinding
import com.example.noteapp.models.News
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private var _binding: Nothing? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val time = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy")
        val formatted = time.format(formatter)
        val text = binding.editText.text.toString()
        val news = News(0,text, formatted)
        App.database.newsDao().insert(news)
        val bundle = bundleOf("news" to news)
        parentFragmentManager.setFragmentResult("rk_news", bundle)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}