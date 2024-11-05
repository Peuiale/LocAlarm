package com.UMDSG.localarm

import android.os.Bundle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SetAlarmFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView : View =  inflater.inflate(R.layout.fragment_set_alarm, container, false)

        val btnRegresar = rootView.findViewById<ImageButton>(R.id.btn_Regresar)
        val btnSeleccionarUbi =rootView.findViewById<Button>(R.id.btn_Select_Ubi)
        val btnSeleccionarUbi2 =rootView.findViewById<Button>(R.id.btn_Select_Ubi_2)

        btnRegresar.setOnClickListener {
            parentFragmentManager.commit {
                replace<PrincipalFragment>(R.id.fcv_main_container)
                setReorderingAllowed(true)
                addToBackStack("principal")
            }
        }

        btnSeleccionarUbi.setOnClickListener {
            parentFragmentManager.commit {
                replace<MapsFragment>(R.id.fcv_main_container)
                setReorderingAllowed(true)
                addToBackStack("principal")
            }
        }
        btnSeleccionarUbi2.setOnClickListener {
            parentFragmentManager.commit {
                replace<MapsFragment2>(R.id.fcv_main_container)
                setReorderingAllowed(true)
                addToBackStack("principal")
            }
        }
        return rootView
    }


    companion object {

        fun newInstance(param1: String, param2: String) =
            SetAlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}