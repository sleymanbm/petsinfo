package com.example.petsinfo.uitel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petsinfo.R
import com.example.petsinfo.uitel.getProgressDrawable
import com.example.petsinfo.uitel.loadImage
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        /**get Data*/
        val animalIntent = intent
        val animalName = animalIntent.getStringExtra("name")
        val animalInfo = animalIntent.getStringExtra("info")
        val animalImg = animalIntent.getStringExtra("img")

        /**call text and images*/
        name.text = animalName
        info.text = animalInfo
        img.loadImage(animalImg, getProgressDrawable(this))
    }
    /**ok now run it */
}