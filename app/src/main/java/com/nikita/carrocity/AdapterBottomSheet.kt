package com.nikita.carrocity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterBottomSheet(val tarifa: ArrayList<Tarifa>) :
    RecyclerView.Adapter<HolderBottomSheet>() {
    var radioButtonCountpos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBottomSheet {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.linea_recycler, parent, false)

        val holder = HolderBottomSheet(vista)
        return holder
    }

    override fun onBindViewHolder(holder: HolderBottomSheet, position: Int) {
        holder.bind(tarifa.get(position))
    }

    override fun getItemCount(): Int {
        return tarifa.size
    }


}