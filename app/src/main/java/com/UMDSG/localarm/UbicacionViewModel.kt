package com.UMDSG.localarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class UbicacionViewModel : ViewModel() {

    // MutableLiveData para almacenar la ubicación actual
    private val _ubicacionActual = MutableLiveData<LatLng>()
    val ubicacionActual: LiveData<LatLng> get() = _ubicacionActual

    // Método para establecer la ubicación actual
    fun setUbicacionActual(ubicacion: LatLng) {
        _ubicacionActual.value = ubicacion
    }



}