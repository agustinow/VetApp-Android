package com.odella.vetapp.controller.vetFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.odella.vetapp.R
import com.odella.vetapp.components.DaggerInfoComponent
import com.odella.vetapp.constants.STATUS_ERROR
import com.odella.vetapp.constants.STATUS_FINISHED
import com.odella.vetapp.constants.STATUS_UNFINISHED
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.vetFragments.vetConsultFragments.ConsultFragmentManager
import com.odella.vetapp.model.Consult
import com.odella.vetapp.model.Pet
import com.odella.vetapp.service.NetworkService
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

    fun checkIfLoaded() {
        if (model.consultByDateStatus.value != STATUS_UNFINISHED && model.consultByNameStatus.value != STATUS_UNFINISHED) {
            view!!.isClickable = true
            view!!.consult_progress_bar.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consult, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //LOGIC
        model = ViewModelProviders.of(activity!!)[VetViewModel::class.java]

        if(model.consultByNameStatus.value != STATUS_FINISHED || model.consultByDateStatus.value != STATUS_FINISHED) {
            view!!.consult_progress_bar.visibility = View.VISIBLE
            view.isClickable = false
            NetworkService.create().getPetsAttendedByOID(model.userSingleton.userID!!).enqueue(
                object :
                    Callback<List<Pet>> {
                    override fun onFailure(call: Call<List<Pet>>, t: Throwable) {
                        model.consultByNameStatus.value = STATUS_ERROR
                        checkIfLoaded()
                    }

                    override fun onResponse(call: Call<List<Pet>>, response: Response<List<Pet>>) {
                        if (response.code() == 200) {
                            model.consultByNameList = response.body()!!.toMutableList()
                            model.consultByNameStatus.value = STATUS_FINISHED
                        } else {
                            model.consultByNameStatus.value = STATUS_ERROR
                        }
                        checkIfLoaded()
                    }
                })
            NetworkService.create().getAllConsultsOfOID(model.userSingleton.userID!!).enqueue(
                object :
                    Callback<List<Consult>> {
                    override fun onFailure(call: Call<List<Consult>>, t: Throwable) {
                        model.consultByDateStatus.value = STATUS_ERROR
                        checkIfLoaded()
                    }

                    override fun onResponse(
                        call: Call<List<Consult>>,
                        response: Response<List<Consult>>
                    ) {
                        if (response.code() == 200) {
                            model.consultByDateList = response.body()!!.toMutableList()
                            model.consultByDateStatus.value = STATUS_FINISHED
                        } else {
                            model.consultByDateStatus.value = STATUS_ERROR
                        }
                        checkIfLoaded()
                    }

                })
        }

        view.view_pager.adapter = ConsultFragmentManager(context!!, childFragmentManager!!)
        view.tabs.setupWithViewPager(view.view_pager)
        //view.tabs.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.time, resources.newTheme())
    }


    companion object {

        @JvmStatic
        fun newInstance() = ConsultFragment()
    }
}
