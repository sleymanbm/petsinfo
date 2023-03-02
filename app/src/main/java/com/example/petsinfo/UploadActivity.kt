package com.example.petsinfo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class UploadActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var infoEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var uploadButton: Button

    private var selectedImageUri: Uri? = null

    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // Initialize Firebase references
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference

        // Initialize views
        nameEditText = findViewById(R.id.edit_text_name)
        infoEditText = findViewById(R.id.edit_text_info)
        imageView = findViewById(R.id.image_view)
        uploadButton = findViewById(R.id.button_upload)

        // Set click listener for image view
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        // Set click listener for upload button
        uploadButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val info = infoEditText.text.toString()

            if (name.isEmpty() || info.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                uploadImage(name, info)
            }
        }
    }

    private fun uploadImage(name: String, info: String) {
        val animalId = databaseReference.child("Animals").push().key ?: ""

        val imageRef = storageReference.child("images/$animalId.jpg")
        imageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val animalData = HashMap<String, Any>()
                    animalData["name"] = name
                    animalData["info"] = info
                    animalData["img"] = uri.toString()

                    databaseReference.child("Animals").child(animalId).setValue(animalData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageView.setImageURI(selectedImageUri)
        }
    }
}
