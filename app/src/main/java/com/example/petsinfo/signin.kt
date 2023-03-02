package com.example.petsinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petsinfo.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class signin : AppCompatActivity() {

        private lateinit var binding: ActivitySigninBinding
        private lateinit var firebaseAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySigninBinding.inflate(layoutInflater)
            setContentView(binding.root)


            firebaseAuth = FirebaseAuth.getInstance()
            binding.textView.setOnClickListener {
                val intent = Intent(this, signup::class.java)
                startActivity(intent)
            }
firebaseAuth = FirebaseAuth.getInstance()
            binding.button.setOnClickListener {
                val email = binding.emailEt.text.toString()
                val pass = binding.passET.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

                }
            }
        }

        override fun onStart() {
            super.onStart()

            if(firebaseAuth.currentUser != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
