package com.example.session_2training

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.lifecycleScope
import com.example.session_2training.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val policyText = "<font color=#a7a7a7>By ticking this box, you agree to our </font> <font color=#ebbc2e>Terms and conditions and private policy</font>"
        binding.policeText.setText(Html.fromHtml(policyText, Html.FROM_HTML_MODE_COMPACT))
        val signInText = "<font color=#a7a7a7>Already have an account? </font> <font color=#0560fa>Sign in</font>"
        binding.signInText.setText(Html.fromHtml(signInText, Html.FROM_HTML_MODE_COMPACT))

        binding.signUp.setOnClickListener {
            val isChecked = binding.isChecked.isChecked

            val fullname = binding.name.text.toString()
            val phone = binding.phone.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeat = binding.confirmPassword.text.toString()

            if (!isChecked) {
                showAlertDialog("You haven't agreed with privacy policy")
                return@setOnClickListener
            }
            if (!email.checkEmail()) {
                showAlertDialog("Wrong email pattern")
                return@setOnClickListener
            }
            if (password != repeat) {
                showAlertDialog("The passwords don't match")
                return@setOnClickListener
            }
            if (fullname.isEmpty() || phone.isEmpty() || password.isEmpty() || email.isEmpty() || repeat.isEmpty()) {
                showAlertDialog("Some fields are blank")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val result = SupabaseConnection.signUp(this@MainActivity, email, password, fullname, phone)
                if (result) {
                    startActivity(Intent(this@MainActivity, LogInActivity::class.java))
                    finish()
                }
            }

        }
        binding.signInText.setOnClickListener {
            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            finish()
        }
        binding.policeText.setOnClickListener {
            val dialog = Dialog(this)

        }
    }
}