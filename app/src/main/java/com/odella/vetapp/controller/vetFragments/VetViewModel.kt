package com.odella.vetapp.controller.vetFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odella.vetapp.App
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.model.*
import javax.inject.Inject


class VetViewModel : ViewModel() {
    val consultByDateStatus: MutableLiveData<Int> = MutableLiveData()
    var consultByNameStatus: MutableLiveData<Int> = MutableLiveData()
    var consultByDateList: MutableList<Consult>? = null
    var consultByNameList: MutableList<Pet>? = null
    var ownersList: List<Owner>? = null
    var idConsult: String = ""
    var consultPetId: String? = null
    @Inject
    lateinit var userSingleton: UserSingleton

    init{
        App.appInjector.inject(this)
    }
}