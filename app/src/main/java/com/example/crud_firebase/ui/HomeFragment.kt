package com.example.crud_firebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crud_firebase.R
import com.example.crud_firebase.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    /*Autenticar usuário*/
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()

        /*configTabLayout()*/

    }





    /*Componete TabLayout*/

    /*private fun configTabLayout(){

        val adapter = ViewPageAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(AgendarFragment(), "Agendar")
        adapter.addFragment(AgendadoFragment(), "Agendado")
        adapter.addFragment(HistoricoFragment(), "Historico")

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ){ tab, position ->
            tab.text = adapter.getTitle(
                position
            )

        }.attach()

    }*/


    private fun initClicks() {


        binding.ibLogout.setOnClickListener { logoutApp() }

        //Direcionar para a Recuperação de Registro
        binding.btnAgendar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_agendarFragment)

        }
    }

    private fun logoutApp(){

        auth.signOut()
        /*Desloga e vai para a página de Login*/
        findNavController().navigate(R.id.action_homeFragment_to_authentication)

    }

}