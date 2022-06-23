package com.nikita.carrocity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.io.ByteArrayOutputStream
import java.lang.ref.Reference
import java.util.*
import kotlin.collections.ArrayList

class Tomar_Foto_Coche : Fragment() {
    //pedir permisos de camara al usuario
    lateinit var openPostActivity: ActivityResultLauncher<Intent>
    lateinit var registerPermisosCamara: ActivityResultLauncher<String>
    lateinit var fotosArray: ArrayList<String>
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fotosArray = ArrayList()
        registerPermisosCamara =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == true) initCamera()
            }

        openPostActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    view?.findViewById<ImageView>(R.id.fotos)
                        ?.setImageBitmap(result.data?.extras?.get("data") as Bitmap)

                    fotosArray.add(MapsActivity.convertirImagenString(result.data?.extras?.get("data") as Bitmap))
                    Log.e("DATO", fotosArray.toString())
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.tomar_foto_coche, container, false)
        val args = arguments?.getString("Datos")

       val benz = MapsActivity.convertirImagenString(
            BitmapFactory.decodeResource(resources,R.drawable.cclass_w_logo)
        )
      /*  val polo = MapsActivity.convertirImagenString(
            BitmapFactory.decodeResource(resources,R.drawable.polo_w_logo)
        )*/
        Log.e("COCHEFOTO",benz)
        /*Log.e("COCHEFOTO","*********************************************************************************************")
        Log.e("COCHEFOTO",polo)*/

        db = FirebaseFirestore.getInstance()

        vista.findViewById<Button>(R.id.botonNextTomarFoto).setOnClickListener {
            Log.e("DATO", fotosArray.size.toString())
            subirfotos(args!!)
            val tipoArg = arguments!!.getString("Tipo")
            val bundle= Bundle()
            bundle.putString("Tipo",tipoArg)
        //    Log.e("TIPO",bundle.toString() +" "+ tipoArg)
            findNavController().navigate(R.id.action_tomar_Foto_Coche_to_cocheReservado,bundle)

        }
        vista.findViewById<FloatingActionButton>(R.id.fabRight).setOnClickListener {
            registerPermisosCamara.launch(android.Manifest.permission.CAMERA)

        }

        vista.findViewById<FloatingActionButton>(R.id.fabLeft).setOnClickListener {
            registerPermisosCamara.launch(android.Manifest.permission.CAMERA)

        }
        vista.findViewById<FloatingActionButton>(R.id.fabTop).setOnClickListener {
            registerPermisosCamara.launch(android.Manifest.permission.CAMERA)

        }
        vista.findViewById<FloatingActionButton>(R.id.fabBottom).setOnClickListener {
            registerPermisosCamara.launch(android.Manifest.permission.CAMERA)

        }


        //metodo para subir fotos al servidor
        return vista
    }




    fun subirfotos(matriculaRef: String) {
        val docRef = db.collection("DamageFoto").document(matriculaRef)

        val fotoNotExist =
            hashMapOf(
                "ArrFotos" to fotosArray,
                "ReferenciaCoche" to db.document("/Coche/" + matriculaRef)
            )

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (i in fotosArray) {
                        docRef.update("ArrFotos", FieldValue.arrayUnion(i))
                    }
                } else {
                    // db.collection("DamageFoto").document(matriculaRef)
                    docRef.collection("DamageFoto").document(matriculaRef).set(fotoNotExist)
                        .addOnSuccessListener{ Log.d("FOTOCOCHE", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("FOTOCOCHE", "Error writing document", e) }
                }
            }.addOnFailureListener{data -> Log.e("FOTOCOCHE","failed to upload "+ data)}

    }

    private fun initCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        openPostActivity.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<TextView>(R.id.fotosSize)?.text = "fotos hechos: " + fotosArray.size
    }
}