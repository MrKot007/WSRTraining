package com.example.a0sessiontry2

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.example.a0sessiontry2.databinding.ActivityHomeBinding
import com.google.common.io.ByteStreams
import kotlinx.coroutines.launch
import java.io.InputStream

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var uriForImage: Uri? = null
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
            loadAvatar()
        }
        val menu = PopupMenu(this, binding.cardAva)
        menu.inflate(R.menu.camera_pick)
        menu.setOnMenuItemClickListener {
            val intent: Intent
            if (it.itemId == R.id.camera) {
                uriForImage = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
                intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForImage)
            }else {
                intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }
            startActivityForResult(intent, 101)
            return@setOnMenuItemClickListener true
        }
        binding.cardAva.setOnClickListener {
            menu.show()
        }
    }
    private fun loadAvatar() {
        lifecycleScope.launch {
            val avatarBitmap = SupabaseConnection.getAvatar(this@HomeActivity)
            if (avatarBitmap != null) {
               binding.ava.setImageBitmap(avatarBitmap)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val pickImageUri: Uri? = data?.data ?: uriForImage
            Log.e("imageSelected", pickImageUri.toString())
            if (pickImageUri != null) {
                val inputStream : InputStream = contentResolver.openInputStream(pickImageUri)!!
                lifecycleScope.launch {
                    val bytes = ByteStreams.toByteArray(inputStream)
                    SupabaseConnection.uploadAvatar(this@HomeActivity, bytes)
                    val newImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.ava.setImageBitmap(newImage)
                }
            }
       }
   }
}