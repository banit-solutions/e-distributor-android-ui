package ke.co.banit.e_distributor_android.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ke.co.banit.e_distributor_android.R
import ke.co.banit.e_distributor_android.core.Category

class HomeCategoryAdapter(onClickListener: (String) -> Unit) :
    ListAdapter<Category, HomeCategoryAdapter.ViewHolder>(CategoryDiffCallback()) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgIcon: ImageView = itemView.findViewById(R.id.img_icon)
        private var txtCategory: TextView = itemView.findViewById(R.id.txt_category)
        private var layoutCategory: LinearLayout = itemView.findViewById(R.id.layout_category)
        fun bind(item: Category?) {
            imgIcon.setImageResource(item?.drawableResource!!)
            txtCategory.text = item.category
            layoutCategory.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}