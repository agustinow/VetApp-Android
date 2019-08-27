package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.adapters.PetsAdapter
import com.odella.vetapp.constants.SEE_ALL_NAMES
import com.odella.vetapp.constants.SEE_ONLY_PET
import com.odella.vetapp.constants.STATUS_FINISHED
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Consult
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_date_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByDateConsultFragment : Fragment() {
    lateinit var adapter: ConsultsAdapter
    lateinit var model: VetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_date_consult, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]
        model.consultByDateStatus.observe(viewLifecycleOwner, Observer<Int> {
            if(it == STATUS_FINISHED && !model.consultByDateList.isNullOrEmpty()){
                loadData()
            } else {
                view.txtNoData.visibility = View.VISIBLE
                view.fragment_by_date_consult_recycler.visibility = View.INVISIBLE
                view.fragment_by_date_consult_search.visibility = View.INVISIBLE
            }
        })
    }

    private fun loadData(){
        adapter = when(UserSingleton.userType!!){
            "vet" -> ConsultsAdapter(context!!, SEE_ONLY_PET) {
                model.idConsult= it.id!!
                //llamar fragmento ViewConsultFragment
            }
            else -> ConsultsAdapter(context!!, SEE_ALL_NAMES) {

            }
        }
        model.consultByDateList
        adapter.setItems(model.consultByDateList!!.toList())


        view!!.txtNoData.visibility = View.GONE
        view!!.fragment_by_date_consult_recycler.visibility = View.VISIBLE
        view!!.fragment_by_date_consult_recycler.adapter = adapter
        view!!.fragment_by_date_consult_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        view!!.fragment_by_date_consult_search.visibility = View.VISIBLE
        view!!.fragment_by_date_consult_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filterByName(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterByName(newText)
                return false
            }
        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = ByDateConsultFragment()
    }
}
