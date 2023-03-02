package com.example.petsinfo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.petsinfo.R
import com.example.petsinfo.uitel.view.NewActivity
import com.example.petsinfo.databinding.ItemListBinding
import com.example.petsinfo.model.AnimalData

class AnimalsAdapter(
    var c:Context,var animalList:ArrayList<AnimalData>
):RecyclerView.Adapter<AnimalsAdapter.AnimalViewHolder>() {
    inner class AnimalViewHolder(var v: ItemListBinding) : RecyclerView.ViewHolder(v.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflter, R.layout.item_list, parent, false
        )
        return AnimalViewHolder(v)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val newList = animalList[position]
        holder.v.isAnimals = animalList[position]
        holder.v.root.setOnClickListener {
            val img = newList.img
            val name = newList.name
            val info = newList.info

            /**set Data*/
            val mIntent = Intent(c, NewActivity::class.java)
            mIntent.putExtra("img", img)
            mIntent.putExtra("name", name)
            mIntent.putExtra("info", info)
            c.startActivity(mIntent)
        }
    }

        override fun getItemCount(): Int {
            return animalList.size
        }
    }
