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
import com.example.crud_firebase.databinding.FragmentRegisterBinding
import com.example.crud_firebase.helper.FirebaseHelper
import com.example.crud_firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

// ...


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Implementando o view binding
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        /*auth = FirebaseAuth.getInstance()*/
        firestore = FirebaseFirestore.getInstance()

        initClicks()
    }

    private fun initClicks(){
        binding.btnRegister.setOnClickListener { validateData() }
    }

    //Validar os dados

    private fun validateData() {
        val nome = binding.edtNome.text.toString().trim()
        val idade = binding.edtIdade.text.toString().toIntOrNull() ?: 0
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (nome.isEmpty() || idade <= 0 || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar.isVisible = true
            registerUser(nome, idade, email, password)
        }
    }




    private fun registerUser(nome: String, idade: Int, email: String, password: String) {


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    val newUser = User(nome, idade, email)



                    // Adicione os dados do usuário à coleção "users" no Firestore
                    firestore.collection("users")
                        .document(user?.uid ?: "")
                        .set(newUser)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Cadastro realizado com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                "Erro ao adicionar dados do usuário: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao cadastrar usuário: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}