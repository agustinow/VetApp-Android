package com.odella.vetapp.controller.vetFragments.vetConsultFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders

import com.odella.vetapp.R
import com.odella.vetapp.constants.formatDate
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Med
import com.odella.vetapp.model.Vacc
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.dialog_pet.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class ViewConsultFragment : Fragment() {
    lateinit var model: VetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_pet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]

        val call = NetworkService.create().getConsult(model.idConsult)
        call.enqueue(object : retrofit2.Callback<Consult> {
            override fun onFailure(call: Call<Consult>, t: Throwable) {

            }

            override fun onResponse(call: Call<Consult>, response: Response<Consult>) {
                if (response.isSuccessful) {
                    val consult = response.body()
                    txtDialogPetName.text = consult?.petName.toString()
                    txtDialogVetName.text = consult?.vetName.toString()
                    txtDialogOwnerName.text=consult?.ownerName.toString()
                    txtDialogDesc.text = consult?.message.toString()
                    txtDialogDate.text = formatDate(consult?.date!!)


                    var arrayMeds: ArrayList<String> = arrayListOf()
                    var arrayVaccs: ArrayList<String> = arrayListOf()

                    consult?.meds!!.forEach {
                        arrayMeds.add(it.name!!)
                    }
                    consult?.vaccs!!.forEach {
                        arrayVaccs.add(it.name!!)
                    }
                    if (arrayMeds.isNullOrEmpty()){
                        spnMeds.visibility = View.GONE
                        txtDialogPetMed.visibility = View.GONE
                    }
                    if (arrayVaccs.isNullOrEmpty()){
                        spnVaccs.visibility = View.GONE
                        txtDialogPetVacc.visibility = View.GONE
                    }
                    val adapterMeds = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, arrayMeds.toArray())
                    val adapterVacc = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, arrayVaccs.toArray())
                    spnMeds!!.adapter = adapterMeds
                    spnVaccs!!.adapter = adapterVacc
                }
            }

        })
        btnPrintConsult.setOnClickListener{

        }
        btnSendMail.setOnClickListener{

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewConsultFragment()
    }
}

