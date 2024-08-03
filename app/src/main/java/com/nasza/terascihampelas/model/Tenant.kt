package com.nasza.terascihampelas.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tenant_table")
data class Tenant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var description: String,
    var imageUri: String? = null
)