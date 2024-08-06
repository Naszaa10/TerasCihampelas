package com.nasza.terascihampelas.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class TenantViewModel : ViewModel() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("tenants")
    val allTenants: LiveData<List<Tenant>> = object : LiveData<List<Tenant>>() {
        init {
            database.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    val tenants = mutableListOf<Tenant>()
                    for (dataSnapshot in snapshot.children) {
                        val tenant = dataSnapshot.getValue(Tenant::class.java)
                        tenant?.let { tenants.add(it) }
                    }
                    postValue(tenants)
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    fun insert(tenant: Tenant) {
        viewModelScope.launch {
            val key = database.push().key
            key?.let {
                database.child(it).setValue(tenant)
            }
        }
    }

    fun update(tenant: Tenant) {
        tenant.id?.let { id ->
            database.child(id).setValue(tenant)
        }
    }

    fun delete(tenant: Tenant) {
        tenant.id?.let { id ->
            database.child(id).removeValue()
        }
    }
}
