package com.example.crud_firebase.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crud_firebase.R
import com.example.crud_firebase.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    /*Autenticar usuário*/
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Implementando o view binding
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    //Depois que a tela é exibida

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Código de tempo da janela de exibição 5s
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, 5000)


    }

    private fun checkAuth(){

        auth = Firebase.auth

        /*Se não estiver autenticado cai para o login*/
        if(auth.currentUser == null){

            findNavController().navigate(R.id.action_splashFragment_to_authentication)
            /*Já está autenticado e cai direto no home*/
        }else{
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}