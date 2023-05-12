package com.example.jobwave.models

import android.os.Parcel
import android.os.Parcelable

class Advertisement (
    var fullName: String = "",
    var email: String = "",
    var contactNumber: String = "",
    var description: String = "",
    var key: String? = null
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(contactNumber)
        parcel.writeString(description)
        parcel.writeString(key)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Advertisement> {
        override fun createFromParcel(parcel: Parcel): Advertisement {
            return Advertisement(parcel)
        }

        override fun newArray(size: Int): Array<Advertisement?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        return "StaffMember(fullName='$fullName', email='$email', contactNumber='$contactNumber', nic='$description',key=$key)"
    }
}