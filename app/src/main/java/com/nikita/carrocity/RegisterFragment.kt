package com.nikita.carrocity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.register_fragment, container, false)



        vista.findViewById<Button>(R.id.nextRegisterFragment).setOnClickListener {

            val email = vista.findViewById<TextInputEditText>(R.id.emailReg).text.toString()
            val password = vista.findViewById<TextInputEditText>(R.id.passwdReg).text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(
                            vista, "Usuario creado", Snackbar.LENGTH_SHORT
                        ).show()

                        val bundle = Bundle()
                        bundle.putString("Email",email)
                        findNavController().navigate(R.id.action_registerFragment_to_driverLicenseFragment,bundle)

                    } else Toast.makeText(
                        activity,
                        "Problemas al crear usuario" + task.exception,
                        Toast.LENGTH_SHORT
                    ).show()
                }


        }
        return vista
    }
}