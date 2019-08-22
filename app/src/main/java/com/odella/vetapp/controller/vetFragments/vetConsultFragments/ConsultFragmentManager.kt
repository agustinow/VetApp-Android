package com.odella.vetapp.controller.vetFragments.vetConsultFragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.odella.vetapp.controller.vetFragments.*

class ConsultFragmentManager(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val TAB_TITLES = arrayOf(
        "By Name",
        "By Date"
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ByNameConsultFragment.newInstance()
            }
            1 -> {
                ByDateConsultFragment.newInstance()
            }
            else ->  ByNameConsultFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

}