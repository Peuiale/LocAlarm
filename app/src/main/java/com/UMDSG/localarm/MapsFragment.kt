package com.UMDSG.localarm

import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: UbicacionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo para ser utilizado.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Verificar permisos y mostrar la ubicación
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Aquí puedes solicitar permisos si no están concedidos
            return
        }
        mMap.isMyLocationEnabled = true

        // Obtener la ubicación actual
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                // Agregar un marcador en la ubicación actual
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Tu ubicación actual").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                // Si deseas mostrar un área más amplia, puedes usar newLatLngBounds
                // Aquí puedes definir un rango alrededor de la ubicación actual
                val builder = LatLngBounds.Builder()
                builder.include(currentLatLng)

                // Si tienes más marcadores, puedes incluirlos aquí
                // builder.include(otraUbicacion)

                // Crear los límites y mover la cámara
                val bounds = builder.build()
                val padding = 100 // Espacio en píxeles alrededor de los límites
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))

                viewModel = ViewModelProvider(requireActivity()).get(UbicacionViewModel::class.java)
                viewModel.setUbicacionActual(currentLatLng)
            }
        }
    }
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permiso concedido, puedes mostrar la ubicación
                onMapReady(mMap)
            } else {
                // Permiso denegado, muestra un mensaje al usuario
            }
        }
    }
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}