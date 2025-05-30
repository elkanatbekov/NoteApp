package com.example.noteapp.ui.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.noteapp.databinding.FragmentProfileBinding
import com.example.noteapp.ui.Prefs

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var _binding: Nothing? = null

    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        imagePickerLauncher =
//            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//                uri?.let {
//                    binding.btnImage.setImageURI(it)
//                }
//            }
//
//        binding.btnImage.setOnClickListener {
//            imagePickerLauncher.launch("image/*")
//        }

        /*binding.editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Prefs(requireContext()).saveName(binding.editText.text.toString() )

            }

        })
        binding.editText.setText(Prefs(requireContext()).getName())*/

//        Prefs(requireContext()).saveName("")
//        binding.editText.setText(Prefs(requireContext()).getName())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
