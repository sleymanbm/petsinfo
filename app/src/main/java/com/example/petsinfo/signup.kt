package com.example.petsinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petsinfo.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {

        lateinit var binding: ActivitySignupBinding
        lateinit var firebaseAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivitySignupBinding.inflate(layoutInflater)
            setContentView(binding.root)

            firebaseAuth = FirebaseAuth.getInstance()

            binding.textView.setOnClickListener {
                val intent = Intent(this, signin::class.java)
                startActivity(intent)
            }
            binding.button.setOnClickListener {
                val email = binding.emailEt.text.toString()
                val pass = binding.passET.text.toString()
                val confirmPass = binding.confirmPassEt.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                    if (pass == confirmPass) {

                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, signin::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                            }
                        }
                    } else {
                        Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

