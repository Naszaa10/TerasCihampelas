package com.nasza.terascihampelas

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.adapter.TenantAdapter
import com.nasza.terascihampelas.model.Tenant
import com.nasza.terascihampelas.model.TenantViewModel

class TenantFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addTenantButton: Button
    private lateinit var tenantViewModel: TenantViewModel
    private var imageChangePosition: Int? = null

    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri ->
            imageChangePosition?.let { position ->
                val tenant = tenantViewModel.allTenants.value?.get(position)
                tenant?.imageUri = selectedUri.toString()
                tenant?.let { tenantViewModel.update(it) }
                imageChangePosition = null
            }
        }
    }

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

        tenantViewModel = TenantViewModel()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        tenantViewModel.allTenants.observe(viewLifecycleOwner, { tenants ->
            recyclerView.adapter = TenantAdapter(tenants.toMutableList(), ::onEditClick, ::onDeleteClick)
        })

        addTenantButton.setOnClickListener {
            showAddTenantDialog()
        }
    }

    private fun onImageChangeClick(position: Int) {
        imageChangePosition = position
        getImageContent.launch("image/*")
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
            tenantViewModel.update(tenant)
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }

    private fun onDeleteClick(tenant: Tenant) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Tenant")
        builder.setMessage("Apakah Anda Yakin Akan Menghapus Tenant?")
        builder.setPositiveButton("Ya Yakin") { _, _ ->
            tenantViewModel.delete(tenant)
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
                name = nameEditText.text.toString(),
                description = descriptionEditText.text.toString()
            )
            tenantViewModel.insert(newTenant)
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }
}
