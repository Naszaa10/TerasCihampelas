package com.nasza.terascihampelas.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nasza.terascihampelas.model.Tenant

@Dao
interface TenantDao {
    @Query("SELECT * FROM tenant_table ORDER BY name ASC")
    fun getAllTenants(): LiveData<List<Tenant>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTenant(tenant: Tenant)

    @Update
    suspend fun updateTenant(tenant: Tenant)

    @Delete
    suspend fun deleteTenant(tenant: Tenant)
}