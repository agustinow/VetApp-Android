package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.odella.vetapp.R
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Med
import com.odella.vetapp.model.Pet
import com.odella.vetapp.model.Vacc
import com.odella.vetapp.model.Vet
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_make_consult.*
import kotlinx.android.synthetic.main.fragment_make_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MakeConsultFragment : Fragment()//, OnBackPressedCallback(true)
{
    /*
    override fun handleOnBackPressed() {
        remove()
    }
    */

    lateinit var meds:List<Med>
    lateinit var vaccs:List<Vacc>
    lateinit var model:VetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_consult, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model=ViewModelProviders.of(activity!!)[VetViewModel::class.java]

        NetworkService.create().getPet(model.consultPetId!!).enqueue(object: Callback<Pet>{
            override fun onFailure(call: Call<Pet>, t: Throwable) {
                //PIJA
            }

            override fun onResponse(call: Call<Pet>, response: Response<Pet>) {
                if (response.isSuccessful){
                    val pet = response.body()
                    view.txt_petname.text = pet?.name ?: ""
                    view.txt_ownername.text = pet?.ownerName ?: ""
                }
            }

        })

        NetworkService.create().getVet(model.userSingleton.userID!!).enqueue(object: Callback<Vet>{
            override fun onFailure(call: Call<Vet>, t: Throwable) {
                //PIJA
            }

            override fun onResponse(call: Call<Vet>, response: Response<Vet>) {
                if(response.isSuccessful){
                    val vet = response.body()
                    view.txt_vetname.text = vet?.name ?: ""
                }
            }
        })


        btnAddMeds.setOnClickListener {
            val call = NetworkService.create().getMeds(model.userSingleton.actualToken!!)
            call.enqueue(object : Callback<List<Med>> {
                override fun onFailure(call: Call<List<Med>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Med>>, response: Response<List<Med>>) {
                    if (response.isSuccessful) {
                    meds=response.body()!!
                    val selectedList = ArrayList<Int>()
                    var arrayMeds: MutableList<CharSequence> = mutableListOf()
                    meds.forEach(){
                        arrayMeds.add(it.name!!)
                    }
                    val builder = AlertDialog.Builder(context!!)
                        .setTitle("Medicine List")
                        .setMultiChoiceItems(arrayMeds.toTypedArray(), null) { dialog, which, isChecked ->
                            if (isChecked) {
                                selectedList.add(which)
                            } else if (selectedList.contains(which)) {
                                selectedList.remove(Integer.valueOf(which))
                            }
                        }
                        .setPositiveButton("DONE") { dialogInterface, i ->
                            var selectedStrings: MutableList<String> = mutableListOf()

                            for (j in selectedList.indices) {
                                selectedStrings.add(arrayMeds[selectedList[j]].toString())
                            }

                            for (element in selectedStrings){
                                Log.d("pedro", element)
                            }
                        }
                         .show()

                    }
                }
            })

        }

        btnAddVaccs.setOnClickListener{
            val call = NetworkService.create().getVaccs(model.userSingleton.actualToken!!)
            call.enqueue(object : retrofit2.Callback<List<Vacc>> {
                override fun onFailure(call: Call<List<Vacc>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Vacc>>, response: Response<List<Vacc>>) {
                    if(response.isSuccessful){
                        vaccs=response.body()!!
                        val selectedList = ArrayList<Int>()
                        var arrayVaccs: MutableList<CharSequence> = mutableListOf()
                        vaccs.forEach(){
                            arrayVaccs.add(it.name!!)
                        }
                        val builder = AlertDialog.Builder(context!!)
                            .setTitle("Vaccin List")
                            .setMultiChoiceItems(arrayVaccs.toTypedArray(), null) { dialog, which, isChecked ->
                                if (isChecked) {
                                    selectedList.add(which)
                                } else if (selectedList.contains(which)) {
                                    selectedList.remove(Integer.valueOf(which))
                                }
                            }
                            .setPositiveButton("DONE") { dialogInterface, i ->
                                var selectedStrings: MutableList<String> = mutableListOf()

                                for (j in selectedList.indices) {
                                    selectedStrings.add(arrayVaccs[selectedList[j]].toString())
                                }

                                for (element in selectedStrings){
                                    Log.d("pedro1", element)
                                }
                            }
                            .show()
                    }
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MakeConsultFragment()
    }
}


