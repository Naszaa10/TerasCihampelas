package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Tenant
import android.widget.Button



class TenantAdapter(
    private val tenants: MutableList<Tenant>,
    private val onImageChangeClick: (Int) -> Unit,
    private val onEditClick: (Tenant) -> Unit,
    private val onDeleteClick: (Tenant) -> Unit
) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tenant, parent, false)
        return TenantViewHolder(view)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val tenant = tenants[position]
        holder.bind(tenant)
    }

    override fun getItemCount(): Int = tenants.size

    inner class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tenantImage: ImageView = itemView.findViewById(R.id.tenant_image)
        private val tenantName: TextView = itemView.findViewById(R.id.tenant_name)
        private val tenantDescription: TextView = itemView.findViewById(R.id.tenant_description)
        private val editTenantButton: Button = itemView.findViewById(R.id.edit_tenant_button)
        private val deleteTenantButton: Button = itemView.findViewById(R.id.delete_tenant_button)

        fun bind(tenant: Tenant) {
            tenantName.text = tenant.name
            tenantDescription.text = tenant.description
            // tenantImage.setImageResource(tenant.imageResourceId) // Uncomment if using drawable resources

            editTenantButton.setOnClickListener {
                onEditClick(tenant)
            }

            deleteTenantButton.setOnClickListener {
                onDeleteClick(tenant)
            }
        }
    }
}