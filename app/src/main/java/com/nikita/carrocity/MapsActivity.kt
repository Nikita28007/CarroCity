package com.nikita.carrocity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.nikita.carrocity.databinding.ActivityMapsBinding
import java.io.ByteArrayOutputStream

class MapsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    DrawerLocker {


    private lateinit var binding: ActivityMapsBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: MaterialToolbar
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerMain)
        toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mapa -> {
                findNavController(R.id.container).navigate(R.id.action_global_maps)
            }

            R.id.puntos -> {
                findNavController(R.id.container).navigate(R.id.action_global_puntos_Fragment)
            }

            R.id.ajustes -> {
                findNavController(R.id.container).navigate(R.id.action_global_ajustes)
            }

            R.id.cerrarsesion -> {
                FirebaseAuth.getInstance().signOut()
                findNavController(R.id.container).navigate(R.id.action_global_loginFragment)
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {


        fun convertirStringBitmap(imagen: String?): Bitmap {
            val decodedString: ByteArray = Base64.decode(imagen, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

        fun convertirImagenString(bitmap: Bitmap): String {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val byte_arr: ByteArray = stream.toByteArray()
            return Base64.encodeToString(byte_arr, Base64.DEFAULT)
        }
    }

    override fun setDrawerEnabled(enabled: Boolean) {
        if (!enabled) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toggle.isDrawerIndicatorEnabled = false
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.isDrawerIndicatorEnabled = true

        }
    }


}