package com.example.basicapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2


class SecondActivity : AppCompatActivity() {

    private val photos = mutableListOf<Uri>()

    private val addPhotoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Add the selected photo to the list of photos and update the adapter
            val newPhotoUri = data?.data
            if (newPhotoUri != null) {
                photos.add(newPhotoUri)
                val viewPager = findViewById<ViewPager2>(R.id.view_pager)
                viewPager.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize the list of photos with some sample images
        photos.addAll(listOf(
            Uri.parse("android.resource://com.example.basicapp/drawable/img1"),
            Uri.parse("android.resource://com.example.basicapp/drawable/img2"),
            Uri.parse("android.resource://com.example.basicapp/drawable/img3")
        ))

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val adapter = ImageAdapter(photos)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val btnAddPhoto = findViewById<Button>(R.id.btn_add_photo)
        btnAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            addPhotoLauncher.launch(intent)
        }
    }
}
