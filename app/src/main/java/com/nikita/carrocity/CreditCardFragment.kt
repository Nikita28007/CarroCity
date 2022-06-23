package com.nikita.carrocity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class CreditCardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.credit_card_fragment, container, false)
        val carnet = arguments?.getParcelable<Carnet>("CARNET")
        val email = arguments?.getString("EMAIL")

        vista.findViewById<Button>(R.id.siguenteCard).setOnClickListener {
            val numeroTarjeta = vista.findViewById<TextInputEditText>(R.id.numCard).text.toString()
            val titular = vista.findViewById<TextInputEditText>(R.id.titularCard).text.toString()
            val fechaExpr =
                vista.findViewById<TextInputEditText>(R.id.fechaExprCard).text.toString()
            val cvv = vista.findViewById<TextInputEditText>(R.id.cvvCard).text.toString()
            if (numeroTarjeta.isNotEmpty() && titular.isNotEmpty() && fechaExpr.isNotEmpty() && cvv.isNotEmpty()) {

                val card = Tarjeta(
                    numeroTarjeta.toInt(),
                    titular,
                    fechaExpr,
                    cvv.toInt()
                )

                val bundle = Bundle()
                bundle.putParcelable("CARNET", carnet)
                bundle.putString("EMAIL", email)
                bundle.putParcelable("CARD", card)
                findNavController().navigate(
                    R.id.action_creditCardFragment_to_userAgreementFragment, bundle
                )
            } else {

                Snackbar.make(
                    vista, "los campos no pueden ser vacios", Snackbar.LENGTH_SHORT
                ).show()

            }
        }

        vista.findViewById<Button>(R.id.addLaterRegister).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("CARNET", carnet)
            bundle.putString("EMAIL", email)
            findNavController().navigate(
                R.id.action_creditCardFragment_to_userAgreementFragment,
                bundle
            )

        }

        return vista
    }
}