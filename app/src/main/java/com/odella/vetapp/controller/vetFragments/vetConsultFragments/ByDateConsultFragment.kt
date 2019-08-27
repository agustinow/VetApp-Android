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
    lateinit var root: View
    lateinit var model: VetViewModel
    lateinit var consultModel: VetConsultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_by_date_consult, container, false)
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]
        //LOGIC
        model.consultByDateStatus.observe(viewLifecycleOwner, Observer<Int> {
            if(it == STATUS_FINISHED && !model.consultByDateList.isNullOrEmpty()){
                loadData()
            } else {
                root.txtNoData.visibility = View.VISIBLE
                root.fragment_by_date_consult_recycler.visibility = View.INVISIBLE
                root.fragment_by_date_consult_search.visibility = View.INVISIBLE
            }
        })
        //END LOGIC

        return root
    }

    private fun loadData(){
        adapter = when(UserSingleton.userType!!){
            "vet" -> ConsultsAdapter(context!!, SEE_ONLY_PET) {
                consultModel.idConsult= it.id!!
                //llamar fragmento ViewConsultFragment
                var frag:Fragment=ViewConsultFragment.newInstance()
                val ft = parentFragment!!.fragmentManager!!.beginTransaction()
                ft.replace(R.id.activiy_vet_content, frag, frag!!.tag)
                ft.commit()
            }
            else -> ConsultsAdapter(context!!, SEE_ALL_NAMES) {

            }
        }
        model.consultByDateList
        adapter.consults = model.consultByDateList!!.toList()
        adapter.setDifferList()
        root.txtNoData.visibility = View.GONE
        root.fragment_by_date_consult_recycler.visibility = View.VISIBLE
        root.fragment_by_date_consult_recycler.adapter = adapter
        root.fragment_by_date_consult_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        root.fragment_by_date_consult_search.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ByDateConsultFragment()
    }
}
