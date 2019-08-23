package com.odella.vetapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.odella.vetapp.R
import com.odella.vetapp.model.Pet

class PetsAdapter(val context: Context) : RecyclerView.Adapter<PetsAdapter.ViewHolder>(){
    var pets: List<Pet> = listOf()
    val differ = AsyncListDiffer<Pet>(this@PetsAdapter, object: DiffUtil.ItemCallback<Pet>(){
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return (oldItem.name == newItem.name && oldItem.age == newItem.age && oldItem.genus == newItem.genus && oldItem.breed == newItem.breed && oldItem.ownerID == newItem.ownerID && oldItem.gender == newItem.gender)
        }

    })

    fun setDifferList(){
        //TEMPORARY
        this.differ.submitList(pets)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_pet, parent, false))
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = differ.currentList[holder.adapterPosition]

        //LOGIC
        holder.txtName.text = pet.name
        holder.txtOwner.text = pet.ownerName
        holder.btnRightArrow.setOnClickListener {
            Toast.makeText(context, "You clicked on ${pet.name}", Toast.LENGTH_SHORT).show()
        }
        //END LOGIC
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val imgPet: ImageView = itemView!!.findViewById(R.id.element_pet_img_genus)
        val txtName: TextView = itemView!!.findViewById(R.id.element_pet_txt_name)
        val txtOwner: TextView = itemView!!.findViewById(R.id.element_pet_txt_owner)
        val btnRightArrow: ImageButton = itemView!!.findViewById(R.id.element_pet_btn_right_arrow)
    }
}