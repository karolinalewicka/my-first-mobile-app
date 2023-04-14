package com.example.basicapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val btnPickPhoto = findViewById<Button>(R.id.btn_select_photo)
        btnPickPhoto.setOnClickListener {
            pickPhoto()
        }
    }

    private fun pickPhoto() {
        val addPhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val intent = Intent().apply {
                    data = uri
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        addPhotoLauncher.launch("image/*")
    }
}
