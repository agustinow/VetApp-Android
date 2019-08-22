package com.odella.vetapp.controller.vetFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.odella.vetapp.R
import com.odella.vetapp.controller.vetFragments.vetConsultFragments.ConsultFragmentManager
import kotlinx.android.synthetic.main.fragment_consult.*


class ConsultFragment : Fragment() {


    lateinit var consultFragmentManager: ConsultFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabs.setupWithViewPager(view_pager)
        tabs.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.time, resources.newTheme())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consult, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ConsultFragment()
    }
}
