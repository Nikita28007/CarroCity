package com.nikita.carrocity

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HolderBottomSheet(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nombreTarifa: TextView
    val precio: TextView
    val precioEspera: TextView
    val radioButton : RadioButton

    init {
        nombreTarifa = itemView.findViewById(R.id.nombreTarifa)
        precio = itemView.findViewById(R.id.precio)
        precioEspera = itemView.findViewById(R.id.precioEspera)
        radioButton = itemView.findViewById(R.id.radioButtonLineaRecycler)

    }


    fun bind(tarifa: Tarifa) {
        nombreTarifa.text =tarifa.nombreTarifa
        precio.text = "Precio "+tarifa.precio.toString() + "€"
        precioEspera.text ="Precio de espera "+ tarifa.precioEspera.toString() + "€"

    }


}