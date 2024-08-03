package com.nasza.terascihampelas.model

import androidx.lifecycle.LiveData
import com.nasza.terascihampelas.database.TenantDao

class TenantRepository(private val tenantDao: TenantDao) {
    val allTenants: LiveData<List<Tenant>> = tenantDao.getAllTenants()

    suspend fun insert(tenant: Tenant) {
        tenantDao.insertTenant(tenant)
    }

    suspend fun update(tenant: Tenant) {
        tenantDao.updateTenant(tenant)
    }

    suspend fun delete(tenant: Tenant) {
        tenantDao.deleteTenant(tenant)
    }
}
