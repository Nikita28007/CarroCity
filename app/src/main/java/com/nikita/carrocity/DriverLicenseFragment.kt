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

class DriverLicenseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.driver_license_fragment, container, false)
        val email = arguments?.getString("Email")


        vista.findViewById<Button>(R.id.registrarSiguenteDriver).setOnClickListener {
            val nombre = vista.findViewById<TextInputEditText>(R.id.nombreCarnet).text.toString()
            val apellido = vista.findViewById<TextInputEditText>(R.id.apellidosCarnet).text.toString()
            val categoria = vista.findViewById<TextInputEditText>(R.id.categoriaCarnet).text.toString()
            val fechaExpr = vista.findViewById<TextInputEditText>(R.id.fechaExprCarnet).text.toString()
            if (nombre.isNotEmpty() && apellido.isNotEmpty() && categoria.isNotEmpty() && fechaExpr.isNotEmpty()) {
                val carnet = Carnet(nombre, apellido, categoria, fechaExpr)
                val bundle = Bundle()
                bundle.putString("EMAIL",email)
                bundle.putParcelable("CARNET",carnet)
                findNavController().navigate(R.id.action_driverLicenseFragment_to_creditCardFragment,bundle)
            } else {
                Snackbar.make(
                    vista,
                    "los campos no pueden ser vacios",
                    Snackbar.LENGTH_SHORT
                ).show()
            }


        }
        return vista
    }
}