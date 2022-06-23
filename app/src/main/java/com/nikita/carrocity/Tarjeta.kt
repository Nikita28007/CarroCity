package com.nikita.carrocity

import android.os.Parcel
import android.os.Parcelable

class Tarjeta() : Parcelable{

    var cardNumber: Int = 0
    var CVV: Int = 0
    lateinit var cardExpireDate: String
    lateinit var titular:String

    constructor(parcel: Parcel) : this() {
        cardNumber = parcel.readInt()
        CVV = parcel.readInt()
        cardExpireDate = parcel.readString()!!
        titular = parcel.readString()!!
    }


    constructor(cardNumber: Int,titular:String, cardExpireDate: String,CVV:Int) : this() {
        this.cardNumber = cardNumber
        this.titular = titular
        this.cardExpireDate = cardExpireDate
        this.CVV = CVV

    }

    constructor(cardNumber: Int, cardExpireDate: String) : this() {
        this.cardNumber = cardNumber
        this.cardExpireDate = cardExpireDate
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cardNumber)
        parcel.writeInt(CVV)
        parcel.writeString(cardExpireDate)
        parcel.writeString(titular)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tarjeta> {
        override fun createFromParcel(parcel: Parcel): Tarjeta {
            return Tarjeta(parcel)
        }

        override fun newArray(size: Int): Array<Tarjeta?> {
            return arrayOfNulls(size)
        }
    }


}