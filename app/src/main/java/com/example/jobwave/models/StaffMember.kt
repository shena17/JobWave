package com.example.jobwave.models

import android.os.Parcel
import android.os.Parcelable

data class StaffMember(
    var fullName: String = "",
    var email: String = "",
    var contactNumber: String = "",
    var nic: String = "",
    var address: String = "",
    var password: String = "",
    var key: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
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
        parcel.writeString(nic)
        parcel.writeString(address)
        parcel.writeString(password)
        parcel.writeString(key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StaffMember> {
        override fun createFromParcel(parcel: Parcel): StaffMember {
            return StaffMember(parcel)
        }

        override fun newArray(size: Int): Array<StaffMember?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "StaffMember(fullName='$fullName', email='$email', contactNumber='$contactNumber', nic='$nic', address='$address', password='$password', key=$key)"
    }
}
