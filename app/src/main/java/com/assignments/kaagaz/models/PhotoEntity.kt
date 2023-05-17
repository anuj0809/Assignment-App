package com.assignments.kaagaz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    val albumId: Int,
    val name: String,
    @PrimaryKey val timeStamp: Long = System.currentTimeMillis()
)