package com.example.session_1speedtraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.session_1speedtraining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        QueueObject.fillQueue()
        val element = QueueObject.controller.getElement()
        binding.image.setImageResource(element.third)
        binding.heading.text = element.first
        binding.paragraph.text = element.second

        binding.next.setOnClickListener {
            QueueObject.controller.removeElement()
            
        }
    }
}