package com.odella.vetapp.controller.vetFragments

import androidx.lifecycle.ViewModel
import com.odella.vetapp.model.*


class VetViewModel : ViewModel() {
    var consultByDateList: MutableList<Consult>? = null
    var consultByNameList: MutableList<Pet>? = null
}