package com.example.session_1speedtraining

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.session_1speedtraining.databinding.ActivityMainBinding
import java.util.Queue

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
            val el = QueueObject.controller.getElement()
            if (QueueObject.controller.getSize() == 1) {
                binding.next.visibility = View.GONE
                binding.skip.visibility = View.GONE
                binding.signUp.visibility = View.VISIBLE
                QueueObject.controller.removeElement()
                binding.signUp.setOnClickListener {
                    startActivity(Intent(this@MainActivity, Holder::class.java))
                    finish()
                }
                binding.toSignIn.setOnClickListener {
                    startActivity(Intent(this@MainActivity, Holder::class.java))
                    finish()
                }
            }
            binding.image.setImageResource(el.third)
            binding.heading.text = el.first
            binding.paragraph.text = el.second
        }
        binding.skip.setOnClickListener {
            QueueObject.clearQueue()
            startActivity(Intent(this@MainActivity, Holder::class.java))
            finish()
        }
        binding.toSignIn.setOnClickListener {
            startActivity(Intent(this@MainActivity, Holder::class.java))
            finish()
        }
    }
}