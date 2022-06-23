package com.nikita.carrocity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class UserAgreementFragment : Fragment() {
    lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.user_agreement_fragment, container, false)
        db = FirebaseFirestore.getInstance()
        val carnet = arguments?.getParcelable<Carnet>("CARNET")
        val tarjeta = arguments?.getParcelable<Tarjeta>("CARD")
        val email = arguments?.getString("EMAIL")
        val checkBox = vista.findViewById<CheckBox>(R.id.userAgreementCheckBox)


        vista.findViewById<Button>(R.id.nextUserAgree).setOnClickListener {
            if (checkBox.isChecked) {
                val user = hashMapOf("Carnet" to carnet, "Email" to email, "Tarjeta" to tarjeta)
                db.collection("Usuarios").document(email!!)
                    .set(user)
                    .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

                findNavController().navigate(R.id.action_userAgreementFragment_to_loginFragment)

            } else {
                Snackbar.make(
                    vista, "no has leido los terminos y condiciones", Snackbar.LENGTH_SHORT
                ).show()
            }
        }



        return vista
    }
}