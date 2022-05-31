package com.kururu.password_manager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(
ForeignKey(entity = AccountType::class,
parentColumns = arrayOf("uid"),
childColumns = arrayOf("type"),

onDelete = CASCADE)
))
data class Account(
    @PrimaryKey(autoGenerate = true ) val uid: Int?,
    @ColumnInfo(name = "type") val accountType: Int,

    @ColumnInfo(name = "emailOrPhone") val emailOrPhone: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "password") val password: String?
)