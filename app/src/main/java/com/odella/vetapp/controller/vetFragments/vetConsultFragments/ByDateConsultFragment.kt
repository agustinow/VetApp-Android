package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.constants.PET_ID
import com.odella.vetapp.constants.SEE_ALL_NAMES
import com.odella.vetapp.constants.SEE_ONLY_PET
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.Consult
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_date_consult.view.*
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByDateConsultFragment : Fragment() {
    lateinit var adapter: ConsultsAdapter
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_by_date_consult, container, false)

        //LOGIC
        loadData()
        //END LOGIC

        return root
    }

    private fun loadData(){
        val mode = when(UserSingleton.userType){
            "vet" -> SEE_ONLY_PET
            "owner" -> SEE_ALL_NAMES
            "admin" -> SEE_ALL_NAMES
            else -> SEE_ALL_NAMES
        }
        adapter = ConsultsAdapter(context!!, mode) {
            var intent = Intent(context!!, PetDialog::class.java)
            intent.putExtra(PET_ID,it.id)
            startActivity(intent)
        }


        val call = when(UserSingleton.userType){
            "vet" -> NetworkService.create().getAllConsultsOfOID(UserSingleton.userID!!)
            "owner" -> TODO()
            else -> NetworkService.create().getAllConsults()
        }
        call.enqueue(object:
            Callback<List<Consult>> {
            override fun onFailure(call: Call<List<Consult>>, t: Throwable) {
                Toast.makeText(context!!, "Network error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Consult>>, response: Response<List<Consult>>) {
                if(response.code() == 200) {
                    adapter.consults = response.body()!!
                    root.fragment_by_date_consult_recycler.adapter = adapter
                    root.fragment_by_date_consult_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                    adapter.setDifferList()
                } else {
                    Toast.makeText(context!!, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ByDateConsultFragment()
    }
}
