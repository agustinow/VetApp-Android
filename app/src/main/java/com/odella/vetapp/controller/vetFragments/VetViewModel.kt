package com.odella.vetapp.controller.vetFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odella.vetapp.model.*


class VetViewModel : ViewModel() {
    val consultByDateStatus: MutableLiveData<Int> = MutableLiveData()
    var consultByNameStatus: MutableLiveData<Int> = MutableLiveData()
    var consultByDateList: MutableList<Consult>? = null
    var consultByNameList: MutableList<Pet>? = null
}