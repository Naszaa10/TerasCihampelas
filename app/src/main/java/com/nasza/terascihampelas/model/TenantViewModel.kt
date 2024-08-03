package com.nasza.terascihampelas.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nasza.terascihampelas.database.AppDatabase
import com.nasza.terascihampelas.model.Tenant
import com.nasza.terascihampelas.model.TenantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TenantRepository
    val allTenants: LiveData<List<Tenant>>

    init {
        val tenantDao = AppDatabase.getDatabase(application).tenantDao()
        repository = TenantRepository(tenantDao)
        allTenants = repository.allTenants
    }

    fun insert(tenant: Tenant) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tenant)
    }

    fun update(tenant: Tenant) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(tenant)
    }

    fun delete(tenant: Tenant) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(tenant)
    }
}

