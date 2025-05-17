package com.example.noteapp.ui.home

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.models.News

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: NewsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::adapter.isInitialized) {
            adapter = NewsAdapter(
                onClick = { position ->
                    val news = adapter.getItem(position)
                    // обработка
                },
                onLongClick = { position ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("Удаление")
                        .setMessage("Удалить новость \"${adapter.getItem(position).title}\"?")
                        .setPositiveButton("Удалить") { _, _ ->
                            adapter.removeItem(position)
                        }
                        .setNegativeButton("Отмена", null)
                        .show()
                }
            )
        }
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.news_Fragment)
        }




        parentFragmentManager.setFragmentResultListener(
            "rk_news",
            viewLifecycleOwner
        ) { _, bundle ->
            val news = bundle.getSerializable("news") as News
            Log.e("Home: ", "text=$news")
            adapter.addItem(news)
        }

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

