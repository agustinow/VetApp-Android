package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.adapters.PetsAdapter
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Pet
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_date_consult.view.*
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByNameConsultFragment : Fragment() {
    lateinit var adapter: PetsAdapter
    lateinit var root: View
    lateinit var model: VetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_by_name_consult, container, false)
        model = ViewModelProviders.of(this@ByNameConsultFragment)[VetViewModel::class.java]
        //LOGIC
        loadData()
        //END LOGIC

        return root

    }

    fun loadData(){
        adapter = PetsAdapter(context!!)
        if(model.consultByNameList != null) {
            adapter.pets = model.consultByNameList!!.toList()
        } else{
            Toast.makeText(context!!, "No data found", Toast.LENGTH_SHORT).show()
            root.txtNoData.visibility = View.VISIBLE
            root.fragment_by_name_consult_recycler.visibility = View.INVISIBLE
            root.fragment_by_name_consult_search.visibility = View.INVISIBLE
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ByNameConsultFragment()
    }
}
