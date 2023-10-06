package com.example.crud_firebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment
import com.example.crud_firebase.R
import com.example.crud_firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment

        // Defina a localização padrão para português do Brasil
        val locale = Locale("pt", "BR")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)


/*        val item1CardView = findViewById<CardView>(R.id.item1CardView)
        item1CardView.setOnClickListener{
            Toast.makeText(this,
                "Item 1 Selecionado",
                Toast.LENGTH_SHORT).show()
        }*/

    }
}