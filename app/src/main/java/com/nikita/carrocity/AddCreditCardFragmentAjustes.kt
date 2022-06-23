package com.nikita.carrocity

import android.graphics.Color
import android.os.Bundle
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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class AddCreditCardFragmentAjustes : Fragment() {
    lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = layoutInflater.inflate(R.layout.add_card_fragment_ajustes, container, false)
        db = FirebaseFirestore.getInstance()


        val user = Firebase.auth.currentUser

        vista.findViewById<Button>(R.id.anyadirAddCardFragmentButton).setOnClickListener {
            val numeroTarjeta =
                vista.findViewById<TextInputEditText>(R.id.numCardAddCardFragmentAjustes).text
            val titular =
                vista.findViewById<TextInputEditText>(R.id.titularAddCardFragmentAjustes).text
            val fechaExpr =
                vista.findViewById<TextInputEditText>(R.id.fechaExprAddCardFragmentAjustes).text
            val cvv =
                vista.findViewById<TextInputEditText>(R.id.cvvAddCardFragmentAjustes).text


            if (numeroTarjeta!!.isNotEmpty() && titular!!.isNotEmpty() && fechaExpr!!.isNotEmpty() && cvv!!.isNotEmpty()) {
                val tarjeta = Tarjeta(
                    numeroTarjeta.toString().toInt(),
                    titular.toString(),
                    fechaExpr.toString(),
                    cvv.toString().toInt()
                )
                val doc = db.collection("Usuarios").document(user?.email.toString())
                    .update("Tarjeta", tarjeta)
                    .addOnSuccessListener {
                        Snackbar.make(
                            vista, "La tarjeta se añadio corectamente", Snackbar.LENGTH_SHORT
                        ).setTextColor(Color.GREEN).show()
                        numeroTarjeta.clear()
                        titular.clear()
                        fechaExpr.clear()
                        cvv.clear()
                    }


            } else {
                Snackbar.make(
                    vista, "los campos no pueden ser vacios", Snackbar.LENGTH_SHORT
                ).setTextColor(Color.RED).setBackgroundTint(Color.BLACK).show()
            }
        }

        vista.findViewById<Button>(R.id.volverAddCardFragmentButton).setOnClickListener{
            findNavController().navigate(R.id.action_addCreditCardFragment_to_ajustes_Fragment)
        }
        return vista
    }
}