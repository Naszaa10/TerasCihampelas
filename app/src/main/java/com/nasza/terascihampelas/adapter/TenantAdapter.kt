package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Tenant
import com.squareup.picasso.Picasso

class TenantAdapter(
    private var tenants: MutableList<Tenant>,
    private val onEditClick: (Tenant) -> Unit,
    private val onDeleteClick: (Tenant) -> Unit
) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    inner class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tenant_name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tenant_description)
        private val imageView: ImageView = itemView.findViewById(R.id.tenant_image)
        private val editButton: Button = itemView.findViewById(R.id.edit_tenant_button)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_tenant_button)

        fun bind(tenant: Tenant, position: Int) {
            nameTextView.text = tenant.name
            descriptionTextView.text = tenant.description
            tenant.imageUri?.let {
                Picasso.get().load(it).into(imageView)
            }

            editButton.setOnClickListener { onEditClick(tenant) }
            deleteButton.setOnClickListener { onDeleteClick(tenant) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tenant, parent, false)
        return TenantViewHolder(view)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        holder.bind(tenants[position], position)
    }

    override fun getItemCount(): Int = tenants.size

    fun updateTenants(newTenants: List<Tenant>) {
        tenants = newTenants.toMutableList()
        notifyDataSetChanged()
    }
}
