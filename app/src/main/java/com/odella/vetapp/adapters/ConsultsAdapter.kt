package com.odella.vetapp.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.odella.vetapp.model.Consult

class ConsultsAdapter(val context: Context, val onClick: (Consult) -> (Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val consults : List<Consult> = listOf()
    val differ = AsyncListDiffer(this@ConsultsAdapter, object: DiffUtil.ItemCallback<Consult>(){
        override fun areItemsTheSame(oldItem: Consult, newItem: Consult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Consult, newItem: Consult): Boolean {
            return (oldItem.petID == newItem.petID && oldItem.vetID == newItem.vetID && oldItem.date == newItem.date)
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO()
    }

}