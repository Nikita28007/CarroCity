package com.nikita.carrocity

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Carnet() : Parcelable {

    //nombre, apellidos, categoria, fecha de expiracion

    lateinit var nombre: String
    lateinit var apellidos: String
    lateinit var categoria: String
    lateinit var fechaDeExpiracion: String

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()!!
        apellidos = parcel.readString()!!
        categoria = parcel.readString()!!
        fechaDeExpiracion = parcel.readString()!!
    }

    constructor(
        nombre: String,
        apellidos: String,
        categoria: String,
        fechaDeExpiracion: String
    ) : this() {
        this.nombre = nombre
        this.apellidos = apellidos
        this.categoria = categoria
        this.fechaDeExpiracion = fechaDeExpiracion

    }

    constructor(nombre: String, apellidos: String, fechaDeExpiracion: String) : this() {
        this.nombre = nombre
        this.apellidos = apellidos
        this.fechaDeExpiracion = fechaDeExpiracion

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(nombre)
        p0?.writeString(apellidos)
        p0?.writeString(categoria)
        p0?.writeString(fechaDeExpiracion)
    }

    companion object CREATOR : Parcelable.Creator<Carnet> {
        override fun createFromParcel(parcel: Parcel): Carnet {
            return Carnet(parcel)
        }

        override fun newArray(size: Int): Array<Carnet?> {
            return arrayOfNulls(size)
        }
    }

}