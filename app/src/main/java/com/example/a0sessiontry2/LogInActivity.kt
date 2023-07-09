package com.example.a0sessiontry2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.lifecycleScope
import com.example.a0sessiontry2.databinding.ActivityLogInBinding
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val signUpText = "<font color=#a7a7a7>Already have an account? </font> <font color=#0560fa>Sign Up</font>"
        binding.signUpText.setText(Html.fromHtml(signUpText, Html.FROM_HTML_MODE_COMPACT))
        binding.signUpText.setOnClickListener {
            startActivity(Intent(this@LogInActivity, MainActivity::class.java))
            finish()
        }
        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showAlertDialog("Some fields are empty")
                return@setOnClickListener
            }
            if (!email.checkEmail()) {
                showAlertDialog("An email doesn't match the pattern")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                SupabaseConnection.logInWithEmail(this@LogInActivity, email, password)
                SupabaseConnection.collectCallback(this@LogInActivity, object : SupabaseConnextionCallback{
                    override fun onAuth(user: UserInfo?) {
                        val sh = getSharedPreferences("main", Context.MODE_PRIVATE)
                        sh.savePasswordSHA512(password)
                        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
                        finish()
                    }
                })
            }

        }
    }
}