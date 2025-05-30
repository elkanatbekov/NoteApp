package com.example.noteapp.ui.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.databinding.FragmentAuthBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var callbacks: OnVerificationStateChangedCallbacks
    private lateinit var verificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Показываем только поле номера и кнопку "Продолжить"
        binding.editPhone.visibility = View.VISIBLE
        binding.btnContinue.visibility = View.VISIBLE

        // Скрываем все остальное до отправки SMS
        binding.editSMS.visibility = View.GONE
        binding.btnVerifyCode.visibility = View.GONE
        binding.tvTimer.visibility = View.GONE
        binding.resendCode.visibility = View.GONE

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.e("Auth", "onVerificationCompleted")
                signIn(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("Auth", "onVerificationFailed: ${p0.message} ")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.d("Auth", "Code sent: $verificationId")
                this@AuthFragment.verificationId = verificationId
                resendToken = token

                Toast.makeText(requireContext(), "Код отправлен", Toast.LENGTH_SHORT).show()

                // Показываем поле для ввода кода и кнопки
                binding.editSMS.visibility = View.VISIBLE
                binding.btnVerifyCode.visibility = View.VISIBLE
                binding.tvTimer.visibility = View.VISIBLE
                binding.resendCode.visibility = View.VISIBLE

                // Скрываем поле ввода телефона и кнопку "Продолжить"
                binding.editPhone.visibility = View.GONE
                binding.btnContinue.visibility = View.GONE

                startTimer()
            }
        }

        binding.btnContinue.setOnClickListener {
            requestSMS()
        }

        binding.btnVerifyCode.setOnClickListener {
            val code = binding.editSMS.text.toString()
            if (code.isEmpty()) {
                Toast.makeText(requireContext(), "Введите текст из SMS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            signIn(credential)
        }

        binding.resendCode.setOnClickListener {
            resendToken.let { token ->
                val phone = binding.editPhone.text.toString()
                val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                    .setPhoneNumber(phone)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(requireActivity())
                    .setCallbacks(callbacks)
                    .setForceResendingToken(token)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

                startTimer()
                binding.resendCode.isEnabled = false
            }
        }

    }

    private fun signIn(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка авторизации ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun requestSMS() {
        val phone = binding.editPhone.text.toString()

        if (phone.isEmpty()) {
            Toast.makeText(requireContext(), "Введите номер телефона", Toast.LENGTH_SHORT).show()
            return
        }

        if (!phone.startsWith("+") || phone.length < 11) {
            Toast.makeText(
                requireContext(),
                "Введите корректный номер телефона в формате +12345678901",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
        }

        timer = object : CountDownTimer(60000, 1000) { // 60 сек, шаг 1 сек
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.tvTimer.text = "Повторная отправка кода через: $seconds сек."
                binding.resendCode.isEnabled = false
            }

            override fun onFinish() {
                binding.tvTimer.text = "Вы можете отправить код повторно"
                binding.resendCode.isEnabled = true
            }
        }.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }
}