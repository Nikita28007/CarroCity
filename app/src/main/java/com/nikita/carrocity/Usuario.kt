package com.nikita.carrocity

class Usuario() {

    lateinit var nombre: String
    lateinit var passwd: String
   // lateinit var carnet: Carnet
   // lateinit var tarjeta: Tarjeta

    //log in
    constructor(nombre: String, passwd: String) : this() {
        this.nombre = nombre
        this.passwd = passwd
    }

    //registro
    constructor(nombre: String, passwd: String, carnet: Carnet, tarjeta: Tarjeta) : this() {
        this.nombre = nombre
        this.passwd = passwd
      //  this.carnet = carnet
        //this.tarjeta = tarjeta
    }

}