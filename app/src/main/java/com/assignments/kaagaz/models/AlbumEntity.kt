package com.assignments.kaagaz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)