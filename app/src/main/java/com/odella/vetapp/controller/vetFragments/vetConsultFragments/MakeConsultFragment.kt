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
import androidx.appcompat.app.AlertDialog

import com.odella.vetapp.R
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.Med
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_make_consult.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MakeConsultFragment : Fragment() {

    lateinit var meds:List<Med>
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


        btnAddMeds.setOnClickListener {
            val call = NetworkService.create().getMeds(UserSingleton.actualToken)
            call.enqueue(object : retrofit2.Callback<List<Med>> {
                override fun onFailure(call: Call<List<Med>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Med>>, response: Response<List<Med>>) {
                    if (response.isSuccessful) {
                    }
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

            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MakeConsultFragment()
    }
}


