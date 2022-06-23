package com.nikita.carrocity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CambiarCorreoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.cambiar_correo_fragment, container, false)

        val user = Firebase.auth.currentUser
        user?.let {
            vista.findViewById<TextView>(R.id.correoActual_cambiarCoreo).text = user.email
        }

        vista.findViewById<Button>(R.id.CambiarCorreoFragmentButton).setOnClickListener {
            val correoNuevo =
                vista.findViewById<TextInputEditText>(R.id.cambiarEmailFragmentEditText).text.toString()
            if (correoNuevo.isNotEmpty()) {
                user!!.updateEmail(correoNuevo)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(
                                vista,
                                "El correo se modificó correctamente",
                                Snackbar.LENGTH_SHORT
                            ).setTextColor(Color.GREEN).show()
                            Log.d("TAG", "User email address updated.")
                        }
                    }

            } else {
                Snackbar.make(
                    vista,
                    "El campo no puede ser vacío",
                    Snackbar.LENGTH_SHORT
                ).setTextColor(Color.RED).setBackgroundTint(Color.BLACK).show()
            }
        }


        vista.findViewById<Button>(R.id.volverCambiarCorreoFragmentButton).setOnClickListener {
            findNavController().navigate(R.id.action_cambiarCorreoFragment_to_ajustes_Fragment)

        }

        return vista

    }
}