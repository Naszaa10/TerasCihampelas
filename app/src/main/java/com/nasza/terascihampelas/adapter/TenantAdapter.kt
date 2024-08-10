package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Tenant
import com.squareup.picasso.Picasso

class TenantAdapter(
    private val tenantList: MutableList<Tenant>,
    private val onEditClick: (Tenant) -> Unit,
    private val onDeleteClick: (Tenant) -> Unit
) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tenant, parent, false)
        return TenantViewHolder(view)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val tenant = tenantList[position]
        holder.bind(tenant)

        // Handle klik tombol edit
        holder.editButton.setOnClickListener {
            onEditClick(tenant)
        }

        // Handle klik tombol hapus
        holder.deleteButton.setOnClickListener {
            onDeleteClick(tenant)
        }
    }

    override fun getItemCount(): Int {
        return tenantList.size
    }

    fun addTenant(tenant: Tenant) {
        tenantList.add(tenant)
        notifyItemInserted(tenantList.size - 1)
    }

    fun removeTenant(tenant: Tenant) {
        val position = tenantList.indexOf(tenant)
        if (position != -1) {
            tenantList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tenantImageView: ImageView = itemView.findViewById(R.id.tenant_image)
        private val tenantNameTextView: TextView = itemView.findViewById(R.id.tenant_name)
        private val tenantDescriptionTextView: TextView = itemView.findViewById(R.id.tenant_description)
        val editButton: Button = itemView.findViewById(R.id.edit_tenant_button)
        val deleteButton: Button = itemView.findViewById(R.id.delete_tenant_button)

        fun bind(tenant: Tenant) {
            tenantNameTextView.text = tenant.name
            tenantDescriptionTextView.text = tenant.description

            // Load gambar menggunakan Picasso atau library lain
            if (tenant.imageUri!!.isNotEmpty()) {
                Picasso.get().load(tenant.imageUri).into(tenantImageView)
            } else {
                tenantImageView.setImageResource(R.drawable.ic_go) // Set gambar placeholder
            }
        }
    }
}
