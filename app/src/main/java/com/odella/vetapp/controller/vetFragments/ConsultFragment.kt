package com.odella.vetapp.controller.vetFragments

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
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.vetConsultFragments.ConsultFragmentManager
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Pet
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_date_consult.view.*
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import kotlinx.android.synthetic.main.fragment_consult.*
import kotlinx.android.synthetic.main.fragment_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ConsultFragment : Fragment() {
    lateinit var model: VetViewModel
    lateinit var consultFragmentManager: ConsultFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //tabs.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.time, resources.newTheme())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_consult, container, false)

        //LOGIC
        model = ViewModelProviders.of(this@ConsultFragment)[VetViewModel::class.java]
        NetworkService.create().getPetsAttendedByOID(UserSingleton.userID!!).enqueue(object:
            Callback<List<Pet>> {
            override fun onFailure(call: Call<List<Pet>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Pet>>, response: Response<List<Pet>>) {
                if(response.code() == 200) {
                    model.consultByNameList = response.body()!!.toMutableList()
                } else {

                }
            }
        })
        NetworkService.create().getAllConsultsOfOID(UserSingleton.userID!!).enqueue(object:
            Callback<List<Consult>> {
            override fun onFailure(call: Call<List<Consult>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Consult>>, response: Response<List<Consult>>) {
                if(response.code() == 200) {
                    model.consultByDateList = response.body()!!.toMutableList()
                } else {

                }
            }

        })




        root.view_pager.adapter = ConsultFragmentManager(context!!, activity!!.supportFragmentManager)
        root.tabs.setupWithViewPager(root.view_pager)
        //root.tabs.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.time, resources.newTheme())
        root.btnNewConsult.setOnClickListener(){
          //NUEVA CONSULTA MEME
          //HACER YA
        }
        //END LOGIC

        return root

    }

    companion object {

        @JvmStatic
        fun newInstance() = ConsultFragment()
    }
}
