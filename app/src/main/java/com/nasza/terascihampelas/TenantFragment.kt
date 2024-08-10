package com.nasza.terascihampelas

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.adapter.TenantAdapter
import com.nasza.terascihampelas.model.Tenant

class TenantFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addTenantButton: Button
    private lateinit var tenantAdapter: TenantAdapter
    private val database = FirebaseDatabase.getInstance()
    private val tenantRef = database.getReference("tenants")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tenant, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        addTenantButton = view.findViewById(R.id.add_tenant_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        tenantRef.get().addOnSuccessListener { dataSnapshot ->
            val tenants = mutableListOf<Tenant>()
            for (snapshot in dataSnapshot.children) {
                val tenant = snapshot.getValue(Tenant::class.java)
                tenant?.let { tenants.add(it) }
            }
            tenantAdapter = TenantAdapter(tenants.toMutableList(), ::onEditClick, ::onDeleteClick)
            recyclerView.adapter = tenantAdapter
        }

        addTenantButton.setOnClickListener {
            showAddTenantDialog()
        }
    }

    private fun onEditClick(tenant: Tenant) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ubah Tenant")

        val view = layoutInflater.inflate(R.layout.dialog_edit_tenant, null)
        val nameEditText = view.findViewById<EditText>(R.id.edit_tenant_name)
        val descriptionEditText = view.findViewById<EditText>(R.id.edit_tenant_description)

        nameEditText.setText(tenant.name)
        descriptionEditText.setText(tenant.description)

        builder.setView(view)
        builder.setPositiveButton("Simpan") { _, _ ->
            tenant.name = nameEditText.text.toString()
            tenant.description = descriptionEditText.text.toString()
            tenant.id?.let { tenantRef.child(it).setValue(tenant) } // Perbarui tenant di Firebase
            tenantAdapter.notifyDataSetChanged() // Beri tahu adapter untuk memperbarui daftar
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }

    private fun onDeleteClick(tenant: Tenant) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Tenant")
        builder.setMessage("Apakah Anda Yakin Akan Menghapus Tenant?")
        builder.setPositiveButton("Ya Yakin") { _, _ ->
            tenant.id?.let { tenantRef.child(it).removeValue() } // Hapus tenant dari Firebase
            tenantAdapter.removeTenant(tenant) // Hapus tenant dari daftar
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }

    private fun showAddTenantDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Tambah Tenant")

        val view = layoutInflater.inflate(R.layout.dialog_edit_tenant, null)
        val nameEditText = view.findViewById<EditText>(R.id.edit_tenant_name)
        val descriptionEditText = view.findViewById<EditText>(R.id.edit_tenant_description)

        builder.setView(view)
        builder.setPositiveButton("Tambah") { _, _ ->
            val newTenant = Tenant(
                id = tenantRef.push().key ?: "", // Buat ID unik untuk tenant
                name = nameEditText.text.toString(),
                description = descriptionEditText.text.toString()
            )
            newTenant.id?.let { tenantRef.child(it).setValue(newTenant) } // Tambahkan tenant baru ke Firebase
            tenantAdapter.addTenant(newTenant) // Tambahkan tenant baru ke daftar
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }
}
