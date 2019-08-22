package com.odella.vetapp.controller.vetFragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class VetFragmentManager(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val TAB_TITLES = arrayOf(
        "Consultation",
        "Pets",
        "Members"
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ConsultFragment.newInstance()
            }
            1 -> {
                PetsFragment.newInstance()
            }
            2 -> {
                MembersFragment.newInstance()
            }
            else -> ConsultFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

}