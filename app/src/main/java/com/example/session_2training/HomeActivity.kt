package com.example.session_2training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.session_2training.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            val user = SupabaseConnection.getUser(this@HomeActivity)
            if (user != null) {
                val fullname = SupabaseConnection.getUserData(this@HomeActivity, user.id)?.full_name ?: ""
                binding.greeting.text = "Hello ${fullname.substringBefore(" ")}"
            }
        }
    }
}