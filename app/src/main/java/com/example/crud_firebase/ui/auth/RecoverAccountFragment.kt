package com.example.crud_firebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.crud_firebase.R
import com.example.crud_firebase.databinding.FragmentLoginBinding
import com.example.crud_firebase.databinding.FragmentRecoverAccountBinding
import com.example.crud_firebase.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {

        binding.btnSend.setOnClickListener { validateData() }

    }

    private fun validateData() {
        val email = binding.edtSend.text.toString().trim()

        /*apresentar mensagem de envio de email*/
        recoverAccountUser(email)

        if (email.isNotEmpty()) {
            binding.progressBar.isVisible = true

        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    /*Função para apresentar mensagem de envio recuperação de senha para o e-mail*/
    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),"Pronto, acabamos de enviar um link para o seu e-mail!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBar.isVisible = false
            }
    }
}
