package com.nikita.carrocity

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class CocheReservado : Fragment() {
    var job: Job? = null
    var jobModoEspera: Job? = null
    lateinit var tipoArg: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.coche_reservado_fragment, container, false)

        tipoArg = arguments?.getString("Tipo")!!
        Log.e("TIPO", tipoArg)
        var cantidadAPagar = 0
        var cantidadModoEspera = 0
        vista.findViewById<Button>(R.id.abrirCocheButton).setOnClickListener {
            vista.findViewById<LinearLayout>(R.id.sesionLayout).visibility = View.VISIBLE
        }

        vista.findViewById<Button>(R.id.inicioCocheReservado).setOnClickListener {
            //iniciar corutina
            cantidadAPagar = startCounter()

        }
        vista.findViewById<Button>(R.id.modoEsperaCocheReservado).setOnClickListener {
            //parar la corutina principal y iniciar otra corutina con el modo espera
            //otra corutina con menos precio
            job?.cancel()
            cantidadModoEspera = modoEspera()
        }

        vista.findViewById<Button>(R.id.stopSessionCocheReservado).setOnClickListener {

            job?.cancel()
            jobModoEspera?.cancel()
            val res = cantidadAPagar + cantidadModoEspera
            Toast.makeText(context, "cantidad a pagar " + res, Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString("EXIT", "cerrar")
            findNavController().navigate(R.id.action_cocheReservado_to_maps, bundle)
            //enviar la nueva dirrecion del coche
        }

        vista.findViewById<Button>(R.id.cerrarCocheCocheReservadoFragment).setOnClickListener {
            Snackbar.make(
                vista,
                "el coche ha sido cerrado correctamente",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return vista
    }

    fun startCounter(): Int {
        val minutos = view?.findViewById<TextView>(R.id.counterResrvadoFragment)
        val minutostest = view?.findViewById<TextView>(R.id.countertest)
        var count = 0
        var countMinutos = 1
        var res = 0
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        job = CoroutineScope(Dispatchers.Main).launch {
            async {
                while (job != null) {
                    minutostest?.text = count.toString()
                    if (count == 30) {

                        when (tipoArg) {

                            "Classic" -> {
                                res += ((countMinutos * 0.70) / 2).toInt()
                                minutos?.text = res.toString()
                            }
                            "Premium" -> {
                                res += ((countMinutos * 0.70) / 2).toInt()
                                minutos?.text = res.toString()
                            }

                        }

                    }
                    if (count == 60) {
                        count = 0
                        countMinutos++
                        //minutos?.text = countMinutos.toString()
                    }

                    withContext(Dispatchers.IO) {
                        delay(1000)
                        count++
                        //minutos?.text = count.toString()
                    }
                }
            }.await()
        }
        /*when (tipoArg) {

            "Classic" -> {
                res = (countMinutos * 0.70).toInt()
            }
            "Premium" -> {
                res = (countMinutos * 1.50).toInt()
            }

        }*/
        return res
    }

    fun modoEspera(): Int {
        var count = 0
        var countMinutos = 0
        CoroutineScope(Dispatchers.IO).launch {
            job = async {
                while (job != null)
                    if (count == 60) {
                        count = 0
                        countMinutos++

                    }

                withContext(Dispatchers.IO) {
                    delay(1000)
                    count++
                }
            }
        }
        var res = 0
        when (tipoArg) {
            "Classic" -> {
                res = (countMinutos * 0.40).toInt()
            }
            "Premium" -> {
                res = (countMinutos * 0.80).toInt()
            }
        }
        return res

    }

}