package com.odella.vetapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.odella.vetapp.R
import com.odella.vetapp.constants.STATUS_UNFINISHED
import com.odella.vetapp.controller.vetFragments.*
import kotlinx.android.synthetic.main.activity_vet.*

class VetActivity : AppCompatActivity() {
    lateinit var sectionsPagerAdapter: VetFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val model: VetViewModel = ViewModelProviders.of(this@VetActivity)[VetViewModel::class.java]
        model.consultByDateStatus.value = STATUS_UNFINISHED
        model.consultByNameStatus.value = STATUS_UNFINISHED
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        sectionsPagerAdapter = VetFragmentManager(this@VetActivity, supportFragmentManager)
       // view_pager_fragments.adapter = sectionsPagerAdapter
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        selectFragment(it)
        false
    }

    fun selectFragment(item: MenuItem) {
        var frag: Fragment? = null
        //checkTimeOut()
        when (item.itemId) {
            R.id.navigation_consult -> frag = ConsultFragment.newInstance()
            R.id.navigation_members -> frag = MembersFragment.newInstance()
            R.id.navigation_pets -> frag = PetsFragment.newInstance()

        }
        // update selected item
        //mSelectedItem = item.itemId
        //updateToolbar(item.title)
        if (frag != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.activiy_vet_content, frag, frag!!.tag)
            ft.commit()
        }
    }

}
