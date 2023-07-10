package com.example.a0sessiontry3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.example.a0sessiontry3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller.fillQueue()
        if (controller.getSize() == 0) {
            binding.nextText.text = "NoText"
        }else {
            binding.nextText.text = "Next"
        }
        val goToSignInText = "<font color=#a7a7a7>Already have an account? </font> <font color=#0560fa>Sign in</font>"
        binding.goToSignIn.setText(Html.fromHtml(goToSignInText, Html.FROM_HTML_MODE_COMPACT))
        val element = controller.getElement()
        binding.image.setImageResource(element.third)
        binding.heading.text = element.first
        binding.paragraph.text = element.second

        binding.next.setOnClickListener {
            controller.removeElement()
            if (controller.getSize() == 1) {
                binding.signUp.visibility = View.VISIBLE
                binding.next.visibility = View.GONE
                binding.skip.visibility = View.GONE

                binding.signUp.setOnClickListener {
                    startActivity(Intent(this@MainActivity, HolderActivity::class.java))
                    finish()
                }
                binding.goToSignIn.setOnClickListener {
                    startActivity(Intent(this@MainActivity, HolderActivity::class.java))
                    finish()
                }
            }
            val el = controller.getElement()
            binding.image.setImageResource(el.third)
            binding.heading.text = el.first
            binding.paragraph.text = el.second
        }
        binding.skip.setOnClickListener {
            startActivity(Intent(this@MainActivity, HolderActivity::class.java))
            finish()
        }
    }
}