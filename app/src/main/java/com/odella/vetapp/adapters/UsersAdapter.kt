package com.odella.vetapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odella.vetapp.R
import com.odella.vetapp.model.Owner

class UsersAdapter(val context: Context, val onClick: (Owner) -> (Unit)): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    var owners: List<Owner> = listOf()
    var filter: Filter = Filter()
    val differ = AsyncListDiffer<Owner>(this@UsersAdapter, object: DiffUtil.ItemCallback<Owner>(){
        override fun areItemsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return (oldItem.username == newItem.username && oldItem.name == newItem.name && oldItem.email == newItem.email)
        }

    })

    fun setItems(owners: List<Owner>){
        this.owners = owners
        setFilter()
    }

    private fun setFilter(){
        var filtered =
            if(filter.name.isNullOrEmpty()){
                //DO NOT FILTER
                owners
            } else {
                owners.filter {
                    it.name?.toLowerCase()?.contains(filter.name!!.toLowerCase()) ?: true || it.username?.toLowerCase()?.contains(filter.name!!.toLowerCase()) ?: true
                }
            }

        differ.submitList(filtered)
    }

    fun filterByName(str: String?){
        filter.name = str
        setFilter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_owner, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val owner = differ.currentList[holder.adapterPosition]

        Glide.with(context).load(owner.image).into(holder.image)
        holder.name.text = owner.name
        holder.user.text = owner.username
        holder.uid.text = owner.memberID.toString()
        holder.layout.setOnClickListener {
            onClick(owner)
        }
    }


    data class Filter(var name: String? = "")

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val layout: ConstraintLayout = itemView!!.findViewById(R.id.element_owner_layout)
        val image: ImageButton = itemView!!.findViewById(R.id.element_owner_btn_image)
        val name: TextView = itemView!!.findViewById(R.id.element_owner_txt_name)
        val user: TextView = itemView!!.findViewById(R.id.element_owner_txt_username)
        val uid: TextView = itemView!!.findViewById(R.id.element_owner_txt_uid)
    }
}