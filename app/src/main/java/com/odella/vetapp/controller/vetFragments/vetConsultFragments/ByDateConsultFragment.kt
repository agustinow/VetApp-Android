package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.Consult
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByDateConsultFragment : Fragment() {
    lateinit var adapter: ConsultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_by_date_consult, container, false)

        //LOGIC
        adapter = ConsultsAdapter(context!!) {
            TODO()
        }
        NetworkService.create().getAllConsultsOfOID(UserSingleton.userID!!).enqueue(object:
            Callback<List<Consult>> {
            override fun onFailure(call: Call<List<Consult>>, t: Throwable) {
                Toast.makeText(context!!, "Network error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Consult>>, response: Response<List<Consult>>) {
                if(response.code() == 200) {
                    adapter.consults = response.body()!!
                    root.fragment_by_name_consult_recycler.adapter = adapter
                } else {
                    Toast.makeText(context!!, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })
        //END LOGIC

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() = ByDateConsultFragment()
    }
}
