package com.odella.vetapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.odella.vetapp.R
import com.odella.vetapp.controller.vetFragments.ConsultFragment
import com.odella.vetapp.controller.vetFragments.VetFragmentManager
import kotlinx.android.synthetic.main.activity_vet.*

class VetActivity : AppCompatActivity() {
    lateinit var sectionsPagerAdapter: VetFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        sectionsPagerAdapter = VetFragmentManager(this@VetActivity, supportFragmentManager)
        view_pager_fragments.adapter = sectionsPagerAdapter
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_consult->{
                TODO()
            }
            R.id.navigation_pets->{
                TODO()
            }
            R.id.navigation_members->{
                TODO()
            }

        }
        false
    }
}
