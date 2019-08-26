package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.adapters.PetsAdapter
import com.odella.vetapp.constants.STATUS_FINISHED
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Pet
import com.odella.vetapp.service.NetworkService
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
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]
        //LOGIC
        model.consultByNameStatus.observe(viewLifecycleOwner, Observer<Int> {
            if(it == STATUS_FINISHED && !model.consultByNameList.isNullOrEmpty()){
                loadData()
            } else {
                root.txtNoData.visibility = View.VISIBLE
                root.fragment_by_name_consult_recycler.visibility = View.INVISIBLE
                root.fragment_by_name_consult_search.visibility = View.INVISIBLE
            }
        })
        //END LOGIC

        return root

    }

    fun loadData(){
        adapter = PetsAdapter(context!!)
        adapter.pets = model.consultByNameList!!.toList()
        adapter.setDifferList()
        root.txtNoData.visibility = View.GONE
        root.fragment_by_name_consult_recycler.visibility = View.VISIBLE
        root.fragment_by_name_consult_recycler.adapter = adapter
        root.fragment_by_name_consult_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        root.fragment_by_name_consult_search.visibility = View.VISIBLE
    }


    companion object {
        @JvmStatic
        fun newInstance() = ByNameConsultFragment()
    }
}
