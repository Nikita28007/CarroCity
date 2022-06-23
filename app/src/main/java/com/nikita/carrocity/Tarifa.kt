package com.nikita.carrocity

class Tarifa() {

    var precioFijo: Int = 0
    lateinit var nombreTarifa: String
    var precio: Double = 0.0
    var precio24H: Int = 0
    var precioEspera: Double = 0.0
   lateinit var precioEsperaGratis: String
    lateinit var tipo: String

    constructor( nombreTarifa:String, precio: Double, precioEspera:Double) : this() {
        this.nombreTarifa = nombreTarifa
        this.precio = precio
        this.precioEspera = precioEspera

    }

    constructor( nombreTarifa:String, precio: Double, precioEspera:String) : this() {
        this.nombreTarifa = nombreTarifa
        this.precio = precio
        this.precioEsperaGratis = precioEspera

    }
}