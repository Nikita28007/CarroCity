package com.nikita.carrocity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CambiarPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.cambiar_password_fragment, container, false)
        val user = Firebase.auth.currentUser

        vista.findViewById<Button>(R.id.CambiarPasswdFragmentButton).setOnClickListener {
            val passwd =
                vista.findViewById<TextInputEditText>(R.id.cambiarPasswdFragmentEditText).text.toString()
            if (passwd.isNotEmpty()) {
                user!!.updatePassword(passwd).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(
                            vista,
                            "La contraseña se modificó correctamente",
                            Snackbar.LENGTH_SHORT
                        ).setTextColor(Color.GREEN).show()
                        Log.d("TAG", "User password updated.")
                    }
                }.addOnFailureListener { task ->
                    Snackbar.make(
                        vista,
                        task.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "User password failed to update." + task.stackTrace + task.message)
                }

            } else {
                Snackbar.make(
                    vista,
                    "El campo no puede ser vacío",
                    Snackbar.LENGTH_SHORT
                ).setTextColor(Color.RED).setBackgroundTint(Color.BLACK).show()
            }
        }

        vista.findViewById<Button>(R.id.volverCambiarPasswdFragmentButton).setOnClickListener {
            findNavController().navigate(R.id.action_cambiarPassword_Fragment_to_ajustes_Fragment)

        }

        return vista
    }
}