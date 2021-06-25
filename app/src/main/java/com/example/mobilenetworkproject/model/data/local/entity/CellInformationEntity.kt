package com.example.mobilenetworkproject.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell_information")
data class CellInformationEntity(
    @PrimaryKey(autoGenerate = true)
    private val id: Int? = null,
    val name: String,

    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "student_id")
    val studentID: String
)