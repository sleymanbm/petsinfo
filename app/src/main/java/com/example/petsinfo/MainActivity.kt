package com.example.petsinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsinfo.adapter.AnimalsAdapter
import com.example.petsinfo.model.AnimalData
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    lateinit var mDataBase: DatabaseReference
    private lateinit var animalList: ArrayList<AnimalData>
    private lateinit var mAdapter: AnimalsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animalList = ArrayList()
        mAdapter = AnimalsAdapter(this, animalList)
        recyclerAnimals.layoutManager = LinearLayoutManager(this)
        recyclerAnimals.setHasFixedSize(true)
        recyclerAnimals.adapter = mAdapter
        getAnimalsData()

    }

    private fun getAnimalsData() {
        mDataBase = FirebaseDatabase.getInstance().getReference("Animals")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (animalSnapshot in snapshot.children) {
                        val animal = animalSnapshot.getValue(AnimalData::class.java)
                        animalList.add(animal!!)
                    }
                    recyclerAnimals.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.copy -> {
                val intent = Intent(this,UploadActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.upload -> {


                return true
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, signin::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }
    }
}

