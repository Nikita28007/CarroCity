package com.nikita.carrocity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class CerrarCocheBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.cerrar_coche_botomsheet, container, false)
        vista.findViewById<Button>(R.id.cerrarCocheBottomSheet).setOnClickListener {
            Snackbar.make(
                vista, "el coche ha sido cerrado correctamente", Snackbar.LENGTH_SHORT
            ).setAnchorView(vista).show()
        }

        return vista
    }


}