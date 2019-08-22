package com.odella.vetapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.odella.vetapp.R
import com.odella.vetapp.model.Consult

class ConsultsAdapter(val context: Context, val onClick: (Consult) -> (Unit)) : RecyclerView.Adapter<ConsultsAdapter.ViewHolder>() {
    var consults : List<Consult> = listOf()

    val differ = AsyncListDiffer(this@ConsultsAdapter, object: DiffUtil.ItemCallback<Consult>(){
        override fun areItemsTheSame(oldItem: Consult, newItem: Consult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Consult, newItem: Consult): Boolean {
            return (oldItem.petID == newItem.petID && oldItem.vetID == newItem.vetID && oldItem.date == newItem.date)
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.element_consult, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val consult = differ.currentList[holder.adapterPosition]
        holder.txtNamePet.text = consult.petName
        holder.txtNameVet.text = consult.vetName
        holder.imgPill.visibility = if(consult.meds!!.isEmpty()) View.INVISIBLE
        else View.VISIBLE
        holder.imgVacc.visibility = if(consult.vaccs!!.isEmpty()) View.INVISIBLE
        else View.VISIBLE
        holder.txtDate.text = consult.date
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val layout: ConstraintLayout = itemView!!.findViewById(R.id.element_consult_layout)
        val imgPet: ImageView = itemView!!.findViewById(R.id.element_consult_img_animal)
        val txtNamePet: TextView = itemView!!.findViewById(R.id.element_consult_txt_pet_name)
        val txtNameVet: TextView = itemView!!.findViewById(R.id.element_consult_txt_vet_name)
        val txtDate: TextView = itemView!!.findViewById(R.id.element_consult_txt_date)
        val imgVacc: ImageView = itemView!!.findViewById(R.id.element_consult_img_vaccine)
        val imgPill: ImageView = itemView!!.findViewById(R.id.element_consult_img_pill)
    }

}