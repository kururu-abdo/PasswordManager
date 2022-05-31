package com.kururu.password_manager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountType(
    @PrimaryKey(autoGenerate = false ,) val uid: Int?,
    @ColumnInfo(name = "accountTypeName") val accountTypeName: String?,
    @ColumnInfo(name = "icon") val icon: Int?
)
