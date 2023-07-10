package com.example.a0sessiontry3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a0sessiontry3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller.fillQueue()
        val element = controller.getElement()
        binding.image.setImageResource(element.third)
        binding.heading.text = element.first
        binding.paragraph.text = element.second

        binding.next.setOnClickListener {
            controller.removeElement()
            if (controller.getSize() == 1) {
                
            }
        }
    }
}