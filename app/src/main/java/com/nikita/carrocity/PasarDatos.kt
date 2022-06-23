package com.nikita.carrocity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PasarDatos : ViewModel() {

    private val datosCoche = MutableLiveData<Coche>()

    val getItem: LiveData<Coche> get() = datosCoche

    fun setItem(item: Coche) {
        datosCoche.value = item
    }


}