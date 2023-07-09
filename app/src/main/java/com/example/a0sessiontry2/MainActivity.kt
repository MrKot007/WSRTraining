package com.example.a0sessiontry2

import android.app.Dialog
import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.lifecycleScope
import com.example.a0sessiontry2.databinding.ActivityMainBinding
import com.pdfview.PDFView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val policyText = "<font color=#a7a7a7>By ticking this box, you agree to our </font> <font color=#ebbc2e>Terms and conditions and private policy</font>"
        binding.policyText.setText(Html.fromHtml(policyText, Html.FROM_HTML_MODE_COMPACT))
        val signInText = "<font color=#a7a7a7>Already have an account? </font> <font color=#0560fa>Sign in</font>"
        binding.signInText.setText(Html.fromHtml(signInText, Html.FROM_HTML_MODE_COMPACT))

        binding.policyText.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.policy_dialog)
            val pdfView = dialog.findViewById<PDFView>(R.id.policy)
            dialog.setOnShowListener {
                pdfView.fromAsset("policy.pdf").show()
            }
            dialog.show()
        }

        binding.signUp.setOnClickListener {
            val isChecked = binding.isChecked.isChecked

            val fullname = binding.name.text.toString()
            val phone = binding.phone.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeat = binding.repeat.text.toString()

            if (fullname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
                showAlertDialog("Fill up all the fields")
                return@setOnClickListener
            }
            if (!email.checkEmail()) {
                showAlertDialog("An email doesn't match the pattern")
                return@setOnClickListener
            }
            if (password != repeat) {
                showAlertDialog("The passwords should be identical")
                return@setOnClickListener
            }
            if (!isChecked) {
                showAlertDialog("Please, get accquainted with our privacy policy")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val result = SupabaseConnection.signUp(this@MainActivity, fullname, phone, email, password)
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
    }
}