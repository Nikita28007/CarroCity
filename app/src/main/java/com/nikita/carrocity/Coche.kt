package com.nikita.carrocity

import com.google.android.gms.maps.model.LatLng

class Coche() {
//gasolina restante, kilometraje total??

    lateinit var matricula: String
    lateinit var marca: String
    lateinit var modelo: String
    lateinit var tipo: String
    lateinit var Direccion: LatLng
    lateinit var Imagen: String
    lateinit var Usuario: Usuario

    constructor(matricula: String, marca: String, modelo: String, tipo: String) : this() {
        this.marca = marca
        this.matricula = matricula
        this.modelo = modelo
        this.tipo = tipo

    }

    constructor(matricula: String, marca: String, modelo: String) : this() {
        this.marca = marca
        this.matricula = matricula
        this.modelo = modelo
    }


    constructor(matricula: String, marca: String, modelo: String,tipo :String,imagen: String) : this() {
        this.matricula = matricula
        this.marca = marca
        this.modelo = modelo
        this.tipo = tipo
        this.Imagen = imagen

    }

    constructor(direccion: LatLng) : this() {
        this.Direccion = direccion
    }


}