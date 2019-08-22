package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.odella.vetapp.R
import com.odella.vetapp.adapters.ConsultsAdapter
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.Consult
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.fragment_by_name_consult.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ByNameConsultFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var root = inflater.inflate(R.layout.fragment_by_name_consult, container, false)
        TODO()
        return root

    }


    companion object {
        @JvmStatic
        fun newInstance() = ByNameConsultFragment()
    }
}
