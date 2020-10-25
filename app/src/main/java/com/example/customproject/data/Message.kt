package com.example.customproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Message(val message: String, val id: String , val timing: String) : Parcelable {
}