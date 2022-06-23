package com.nikita.carrocity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private val model: PasarDatos by activityViewModels()
    var listenerRegistration: ListenerRegistration? = null
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    lateinit var vista: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.activity_maps, container, false)
        (activity as DrawerLocker?)!!.setDrawerEnabled(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        firebaseFirestore = FirebaseFirestore.getInstance()

        val args = arguments?.getString("EXIT")
        if (args == "cerrar"){
            findNavController().navigate(R.id.action_maps_to_cerrar_Coche_BottomSheet2)
        }
         /*val coche = firebaseFirestore.collection("Coche").document("0001LNG")
         coche.update("Imagen",MapsActivity.convertirImagenString(BitmapFactory.decodeResource(resources, R.drawable.bmwm8_carrocity))).addOnSuccessListener { Log.d("BASE", "DocumentSnapshot successfully updated!") }
             .addOnFailureListener { e -> Log.w("BASE", "Error updating document", e) }*/
        /*val polo = MapsActivity.convertirImagenString(
            BitmapFactory.decodeResource(resources, R.drawable.renault_arcana_carrocity)
        )
        Log.e("Benz", polo.length.toString())*/


        /*val benz = firebaseFirestore.collection("Coche").document("HtZCs9gjeDlEJuCdpSMy")
        benz.update("Imagen",MapsActivity.convertirImagenString(BitmapFactory.decodeResource(resources, R.drawable.cclass_w_logo)))*/

        /*val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email.toString()
            view?.findViewById<TextView>(R.id.menuEmail)?.text = email
        }*/
        return vista
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val fab = vista.findViewById<FloatingActionButton>(R.id.fabGeo)
        val targetAlicante = LatLng(38.346128522083006, -0.4919216626618416)
        val cam = CameraPosition.fromLatLngZoom(targetAlicante, 13F)


        Log.e("DATO", " dfhfdh")
        Log.e("DATO", "" + firebaseFirestore.collection("Coche").toString().trimIndent())
        addMarkers(mMap)

        // mMap.setPadding(0,0, 900,90)

        if (ActivityCompat.checkSelfPermission(
                vista.context, Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                vista.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        fab.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
                val loc = Location(LocationManager.GPS_PROVIDER)


                /*loc.latitude = 37.99788206881422
                loc.longitude = -0.6907860696942535
                location.set(loc)*/
                val lastKnownPos = CameraPosition.fromLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    13F
                )
                val currentPos = LatLng(loc.latitude, loc.longitude)
                mMap.addMarker(
                    MarkerOptions().position(currentPos).icon(
                        bitmapDescriptorFromVector(
                            context!!,
                            R.drawable.ic_baseline_circle_24
                        )
                    )
                )
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(lastKnownPos))


            }
        }


        mMap.setOnMarkerClickListener { marker ->
            val split = marker.title!!.split(" ")
            Log.e("DATO", split.toString())
            val coche = Coche(split[0], split[1], split[2], split[3], split[4])
            model.setItem(coche)
            findNavController().navigate(R.id.action_maps_to_bottomSheet)
            true
        }

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context!!, R.raw.map1))
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cam))


    }

    private fun addMarkers(mMap: GoogleMap) {
        val direcciones = firebaseFirestore.collection("Coche")
        listenerRegistration = direcciones.addSnapshotListener { value, error ->
            if (error == null) {
                for (doc in value!!) {
                    val geoPoint = doc.getGeoPoint("Direccion")
                    val latitude: Double = geoPoint!!.latitude
                    val longitude: Double = geoPoint.longitude
                    val coches = LatLng(latitude, longitude)

                    when (doc.data["Tipo"]) {
                        "Classic" -> {
                            val carInfo =
                                doc.data["Matricula"].toString() + " " + doc.data["Marca"].toString() + " " + doc.data["Modelo"].toString() + " " + doc.data["Tipo"] + " " + doc.data["Imagen"]
                            mMap.addMarker(
                                MarkerOptions().position(coches).icon(
                                    bitmapDescriptorFromVector(
                                        context!!,
                                        R.drawable.ic_baseline_car_rental_24
                                    )
                                ).title(carInfo)
                            )
                        }
                        "Premium" -> {
                            val carInfo =
                                doc.data["Matricula"].toString() + " " + doc.data["Marca"].toString() + " " + doc.data["Modelo"].toString() + " " + doc.data["Tipo"] + " " + doc.data["Imagen"]
                            mMap.addMarker(
                                MarkerOptions().position(coches).icon(
                                    bitmapDescriptorFromVector(
                                        context!!,
                                        R.drawable.ic_premium_car_rental_24
                                    )
                                ).title(carInfo)
                            )
                        }
                        else -> {
                        }
                    }


                }

            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration?.remove()
    }
}