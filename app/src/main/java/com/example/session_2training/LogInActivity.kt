package com.example.session_2training

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.lifecycleScope
import com.example.session_2training.databinding.ActivityLogInBinding
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val signInText = "<font color=#a7a7a7>Already have an account? </font> <font color=#0560fa>Sign Up</font>"
        binding.signUpText.setText(Html.fromHtml(signInText, Html.FROM_HTML_MODE_COMPACT))

        binding.signUpText.setOnClickListener {
            startActivity(Intent(this@LogInActivity, MainActivity::class.java))
            finish()
        }

        binding.signIn.setOnClickListener {
            val email = binding.emailLog.text.toString()
            val password = binding.passwordLog.text.toString()
            if (!email.checkEmail()) {
                showAlertDialog("An email doesn't match the pattern")
                return@setOnClickListener
            }
            if (password.isEmpty() || email.isEmpty()) {
                showAlertDialog("Fill in all the fields")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                SupabaseConnection.logInViaEmail(this@LogInActivity, email, password)
                SupabaseConnection.collectCallback(this@LogInActivity, object: SupabaseConnectionCallback{
                    override fun onAuth(user: UserInfo?) {
                        val sh = getSharedPreferences("main", Context.MODE_PRIVATE)
                        sh.savePasswordSHA512(password)
                        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
                        finish()
                    }

                    override fun onLoading() {
                    }

                    override fun onNetworkError() {
                        showAlertDialog("Something went wrong. Try again")
                    }

                    override fun onUnauthorizedEntry() {
                        showAlertDialog("Failed to log in")
                    }
                })
            }
        }
    }
}