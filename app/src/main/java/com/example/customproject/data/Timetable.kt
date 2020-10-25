package com.example.customproject.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Timetable(@PrimaryKey(autoGenerate = true) val id: Int, var day: String ="", var time: String ="", var isAvailable: Int = 0) : Parcelable {}