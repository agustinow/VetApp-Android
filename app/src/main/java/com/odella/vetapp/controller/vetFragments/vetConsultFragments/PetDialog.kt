package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.odella.vetapp.R
import com.odella.vetapp.constants.PET_ID
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Med
import com.odella.vetapp.model.Vacc
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.dialog_pet.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class PetDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pet)
        val id = intent.getStringExtra(PET_ID)
        val call = NetworkService.create().getConsult(id)
        call.enqueue(object : retrofit2.Callback<Consult> {
            override fun onFailure(call: Call<Consult>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Consult>, response: Response<Consult>) {
                if(response.isSuccessful){
                    val consult = response.body()
                    txtDialogPetName.text=consult?.petName!!.toString()
                    txtDialogVetName.text=consult?.vetName!!.toString()
                    txtDialogDesc.text=consult?.message!!.toString()
                    txtDialogDate.text=consult?.date.toString()


                    var arrayMeds: ArrayList<Med> = arrayListOf()
                    var arrayVaccs:ArrayList<Vacc> = arrayListOf()

                    consult.meds!!.forEach {
                        arrayMeds.add(it)
                    }
                    consult.vaccs!!.forEach {
                        arrayVaccs.add(it)
                    }


                    val adapterMeds = ArrayAdapter(this@PetDialog,android.R.layout.simple_spinner_dropdown_item,arrayMeds.toArray())
                    val adapterVacc = ArrayAdapter(this@PetDialog,android.R.layout.simple_spinner_dropdown_item,arrayVaccs.toArray())
                    spnMeds!!.adapter = adapterMeds
                    spnVaccs!!.adapter = adapterVacc
                }
            }

        })

    }
}
