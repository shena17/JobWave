package com.example.jobwave.models

import android.os.Parcel
import android.os.Parcelable

class Company(
    var companyName: String = "",
    var Owner: String = "",
    var email: String = "",
    var contactNumber: String = "",
    var address: String = "",
    var industry: String = "",
    var description: String = "",
    var status: String? = "pending",
    var key: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(companyName)
        parcel.writeString(Owner)
        parcel.writeString(email)
        parcel.writeString(contactNumber)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeString(industry)
        parcel.writeString(status)
        parcel.writeString(key)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Company> {
        override fun createFromParcel(parcel: Parcel): Company {
            return Company(parcel)
        }

        override fun newArray(size: Int): Array<Company?> {
            return arrayOfNulls(size)
        }
    }
}