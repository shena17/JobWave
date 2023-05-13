package com.example.jobwave.models

import android.os.Parcel
import android.os.Parcelable

data class Employer(
    var title: String = "",
    var description: String = "",
    var skills: String = "",
    var salary:String = "",

    var id:String= "",
    var date:String?=null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?:"",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(skills)
        parcel.writeString(salary)
        parcel.writeString(id)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Employer> {
        override fun createFromParcel(parcel: Parcel): Employer {
            return Employer(parcel)
        }

        override fun newArray(size: Int): Array<Employer?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Employer(title='$title', description='$description', skills='$skills', salary='$salary', id='$id', date='$date')"
    }
}



