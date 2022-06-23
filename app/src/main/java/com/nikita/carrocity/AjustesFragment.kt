package com.nikita.carrocity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AjustesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista =
            LayoutInflater.from(context).inflate(R.layout.ajustes_fragment, container, false)

        vista.findViewById<Button>(R.id.addTarjetaAjustesButton).setOnClickListener {
            findNavController().navigate(R.id.action_ajustes_Fragment_to_addCreditCardFragment)
        }

        vista.findViewById<Button>(R.id.cambiarEmailAjustesButton).setOnClickListener {
            findNavController().navigate(R.id.action_ajustes_Fragment_to_cambiarCorreoFragment)
        }

        vista.findViewById<Button>(R.id.cambiarPasswdAjustesButton).setOnClickListener {
            findNavController().navigate(R.id.action_ajustes_Fragment_to_cambiarPassword_Fragment)
        }


        vista.findViewById<Button>(R.id.eliminarCuentaAjustesButton).setOnClickListener {
            val builder = AlertDialog.Builder(vista.context)

            builder.setTitle("Eliminar Cuenta?")
            builder.setMessage("Estás apunto de eliminar tu cuenta. ¿Estas seguro?")
            builder.setPositiveButton("Eliminar") { dialog, id ->
                val user = Firebase.auth.currentUser!!

                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(
                                vista, "La cuenta se eliminó correctamente",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.d("TAG", "User account deleted.")
                            FirebaseAuth.getInstance().signOut()
                            findNavController().navigate(R.id.action_ajustes_Fragment_to_loginFragment)
                        }
                    }

            }

            builder.setNegativeButton(

                "Cancelar"
            ) { dialog, id ->
                // User cancelled the dialog
            }
            builder.show()

        }


        return vista
    }
}