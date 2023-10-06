package com.example.crud_firebase.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.crud_firebase.R
import com.example.crud_firebase.databinding.FragmentLoginBinding
import com.example.crud_firebase.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Implementando o view binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        initClicks()
    }

    //Ouvir eventos de clique

    private fun initClicks(){

        binding.btnLogin.setOnClickListener { validateData() }

        //Direcionar para o Registro
        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.registerFragment)

        }

        //Direcionar para a Recuperação de Registro
        binding.btnRecover.setOnClickListener{
            findNavController().navigate(R.id.recoverAccountFragment)

        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if(password.isNotEmpty()){

                binding.progressBar.isVisible = true

                loginUser(email, password)

            } else {
                Toast.makeText(requireContext(), "Informe a sua senha", Toast.LENGTH_LONG).show()

            }
        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_LONG).show()
        }
    }

    private fun loginUser( email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}