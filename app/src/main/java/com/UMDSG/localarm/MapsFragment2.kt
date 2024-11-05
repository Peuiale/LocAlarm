package com.UMDSG.localarm

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.UMDSG.localarm.MapsFragment.Companion
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment2 : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        checkLocationPermission()

        // Configurar el listener para clics en el mapa
        mMap.setOnMapClickListener { latLng ->
            // Llamar a la función para manejar la selección de ubicación
            onMapLocationSelected(latLng)
        }
    }

    private fun checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MapsFragment.LOCATION_PERMISSION_REQUEST_CODE)
        }else {
            mMap.isMyLocationEnabled = true
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Tu ubicación actual"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun onMapLocationSelected(latLng: LatLng) {
        // Aquí puedes manejar la ubicación seleccionada
        // Por ejemplo, agregar un marcador en la ubicación seleccionada
        mMap.addMarker(MarkerOptions().position(latLng).title("Ubicación seleccionada"))

        // También puedes mover la cámara a la ubicación seleccionada
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        // Aquí puedes realizar otras acciones, como guardar la ubicación seleccionada
        // o enviarla a otra actividad o fragmento
        val bundle = Bundle()
        bundle.putParcelable("ubicacion_seleccionada", latLng)

        // Enviar el Bundle al otro fragmento
        val fragment = SetAlarmFragment()
        fragment.arguments = bundle

        // Reemplazar el fragmento actual con el nuevo fragmento
        parentFragmentManager.commit {
            replace<SetAlarmFragment>(R.id.fcv_main_container)
            setReorderingAllowed(true)
            addToBackStack("principal")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                checkLocationPermission() // Vuelve a verificar los permisos
            } else {
                // Permiso denegado, muestra un mensaje al usuario
            }
        }
    }
}