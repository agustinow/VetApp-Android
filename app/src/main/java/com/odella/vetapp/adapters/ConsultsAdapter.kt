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
import com.odella.vetapp.constants.SEE_ALL_NAMES
import com.odella.vetapp.constants.SEE_ONLY_PET
import com.odella.vetapp.constants.SEE_ONLY_VET
import com.odella.vetapp.constants.formatDate
import com.odella.vetapp.model.Consult
import java.text.SimpleDateFormat
import java.util.*

class ConsultsAdapter(val context: Context, val mode: Int, val onClick: (Consult) -> (Unit)) : RecyclerView.Adapter<ConsultsAdapter.ViewHolder>() {
    var filter: Filter = Filter()
    private var consults : List<Consult> = listOf()

    private val differ = AsyncListDiffer(this@ConsultsAdapter, object: DiffUtil.ItemCallback<Consult>(){
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

    fun setItems(consults: List<Consult>){
        this.consults = consults
        setFilter()
    }

    private fun setFilter(){
        var filtered =
            if(filter.name.isNullOrEmpty()){
                //DO NOT FILTER
                consults
            } else {
                consults.filter {
                    it.vetName?.toLowerCase()?.contains(filter.name!!.toLowerCase()) ?: true || it.petName?.toLowerCase()?.contains(filter.name!!.toLowerCase()) ?: true
                }
            }

        differ.submitList(filtered)
    }

    fun filterByName(str: String?){
        filter.name = str
        setFilter()
    }

    data class Filter(var name: String? = "")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val consult = differ.currentList[holder.adapterPosition]
        holder.txtNamePet.text = consult.petName
        holder.txtNameVet.text = consult.vetName
        when (mode) {
            SEE_ALL_NAMES -> {
                holder.txtNameVet.visibility = View.VISIBLE
                holder.txtNamePet.visibility = View.VISIBLE
            }
            SEE_ONLY_PET -> holder.txtNamePet.visibility = View.VISIBLE
            SEE_ONLY_VET -> holder.txtNameVet.visibility = View.VISIBLE
            else -> {
                holder.txtNamePet.text = formatDate(consult.date!!)
                holder.txtNamePet.visibility = View.VISIBLE
                holder.txtDate.visibility = View.GONE
                val density = context.resources.displayMetrics.density;
                holder.imgPill.layoutParams.height = (45 * density).toInt()
                holder.imgPill.layoutParams.width = (45 * density).toInt()
                holder.imgVacc.layoutParams.height = (45 * density).toInt()
                holder.imgVacc.layoutParams.width = (45 * density).toInt()
            }
        }
        holder.imgPill.visibility = if(consult.meds!!.isEmpty()) View.INVISIBLE
        else View.VISIBLE
        holder.imgVacc.visibility = if(consult.vaccs!!.isEmpty()) View.INVISIBLE
        else View.VISIBLE
        holder.txtDate.text = formatDate(consult.date!!)
        holder.layout.setOnClickListener{
            onClick(consult)
        }
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