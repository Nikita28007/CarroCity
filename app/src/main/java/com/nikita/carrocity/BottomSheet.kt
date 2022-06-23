package com.nikita.carrocity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {
    private val model: PasarDatos by activityViewModels()
    lateinit var Tipo: String
    lateinit var matricula: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.bottom_sheet, container, false)


       /* val benz = MapsActivity.convertirImagenString(
            BitmapFactory.decodeResource(resources, R.drawable.bmwx3_carrocity)
        )
        Log.e("Benz", benz)*/

      /*  val polo = MapsActivity.convertirImagenString(
            BitmapFactory.decodeResource(resources, R.drawable.polo_w_logo)
        )
        Log.e("Benz", polo)*/

        val nameObserver = Observer<Coche> { coche ->
            vista.findViewById<TextView>(R.id.matriculaBottomSheetReservar).setText(coche.matricula)
            vista.findViewById<TextView>(R.id.marcaBottomSheet).text = coche.marca
            vista.findViewById<TextView>(R.id.modeloBottomSheet).text = coche.modelo
            vista.findViewById<ImageView>(R.id.imagenCoche).setImageBitmap(MapsActivity.convertirStringBitmap(coche.Imagen))
            Tipo = coche.tipo
            matricula = coche.matricula

        }
        model.getItem.observe(requireActivity(), nameObserver)


        initRecycler(vista)
        vista.findViewById<Button>(R.id.botonReservarBottomSheet).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Datos", matricula)
            bundle.putString("Tipo",Tipo)
            findNavController().navigate(R.id.action_bottomSheet_to_tomar_Foto_Coche, bundle)
        }


        return vista
    }

    private fun initRecycler(vista: View) {
        val recyclerView = vista.findViewById<RecyclerView>(R.id.recyclerTarifa)
        val arr = ArrayList<Tarifa>()
        var adapter: AdapterBottomSheet? = null
        Log.e("DATO", Tipo)
        when (Tipo) {
            "Classic" -> {
                arr.add(Tarifa("Por minutos", 0.40, 0.20))
                arr.add(Tarifa("24H", 35.0, "gratis"))
                arr.add(Tarifa("7 Dias", 70.0, 0.0))
                adapter = AdapterBottomSheet(arr)
            }
            "Premium" -> {
                arr.add(Tarifa("Por minutos", 0.90, 0.50))
                arr.add(Tarifa("24H", 120.0, "gratis"))
                arr.add(Tarifa("7 Dias", 260.0, "gratis"))
                adapter = AdapterBottomSheet(arr)
            }

        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(vista.context, LinearLayoutManager.HORIZONTAL, false)
    }


}