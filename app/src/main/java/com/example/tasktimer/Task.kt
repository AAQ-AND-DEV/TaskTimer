package com.example.tasktimer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Task(val name: String, val desc: String, val sortOrder: Int): Parcelable {
    var id: Long = 0
}