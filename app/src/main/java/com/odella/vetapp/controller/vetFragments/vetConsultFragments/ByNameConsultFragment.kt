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
import com.odella.vetapp.constants.SEE_NOTHING
import com.odella.vetapp.constants.SEE_ONLY_PET
import com.odella.vetapp.constants.STATUS_FINISHED
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Pet
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import kotlinx.android.synthetic.main.fragment_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByNameConsultFragment : Fragment() {
    lateinit var adapter: PetsAdapter
    lateinit var conAdapter: ConsultsAdapter
    lateinit var model: VetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_by_name_consult, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]
        model.consultByNameStatus.observe(viewLifecycleOwner, Observer<Int> {
            if(it == STATUS_FINISHED && !model.consultByNameList.isNullOrEmpty()){
                loadData()
            } else {
                view.txtNoData.visibility = View.VISIBLE
                view.fragment_by_name_consult_recycler.visibility = View.INVISIBLE
                view.fragment_by_name_consult_search.visibility = View.INVISIBLE
            }
        })
        view!!.btn_by_name_back.setOnClickListener {
            changeToPetList()
        }
    }
    fun loadData(){
        adapter = PetsAdapter(context!!) {
            changeToConsultList(it)
        }
        adapter.pets = model.consultByNameList!!.toList()
        adapter.setDifferList()
        view!!.txtNoData.visibility = View.GONE
        view!!.fragment_by_name_consult_recycler.visibility = View.VISIBLE
        view!!.fragment_by_name_consult_recycler.adapter = adapter
        view!!.fragment_by_name_consult_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        view!!.fragment_by_name_consult_search.visibility = View.VISIBLE
    }

    fun changeToConsultList(pet: Pet){
        conAdapter = ConsultsAdapter(context!!, SEE_NOTHING){
            //OPEN INFO
            model.idConsult= it.id!!
            var frag:Fragment=ViewConsultFragment.newInstance()
            val ft = parentFragment!!.fragmentManager!!.beginTransaction()
            ft.replace(R.id.activiy_vet_content, frag, frag!!.tag)
            ft.commit()
        }
        val preFilteredConsults= model.consultByDateList!!.toList()
        var finalConsults = mutableListOf<Consult>()
        for (consult in preFilteredConsults){
            if(consult.petID == pet.id) finalConsults.add(consult)
        }
        conAdapter.setItems(finalConsults)
        view!!.fragment_by_name_consult_recycler.adapter = conAdapter
        view!!.btnNewConsult.visibility = View.VISIBLE
        view!!.btn_by_name_back.visibility = View.VISIBLE
    }

    fun changeToPetList(){
        view!!.fragment_by_name_consult_recycler.adapter = adapter
        view!!.btnNewConsult.visibility = View.GONE
        view!!.btn_by_name_back.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ByNameConsultFragment()
    }
}
