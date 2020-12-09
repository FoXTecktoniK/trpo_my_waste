package com.example.mywaste.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywaste.R
import com.example.mywaste.databinding.WasteListItemBinding
import com.example.mywaste.domain.WasteModel
import java.util.Calendar

class WasteAdapter : ListAdapter<WasteModel, WasteAdapter.WasteViewHolder>(WasteModelDiff()) {

    class WasteViewHolder(private val viewBinding: WasteListItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(model: WasteModel) {
            viewBinding.categoryTv.text = model.categoryName
            viewBinding.shopTv.text = model.shop
            viewBinding.totalAmountTv.text = "${model.totalRUR} ₽"
            viewBinding.dateTv.text = "${model.date.get(Calendar.DAY_OF_MONTH)}.${model.date.get(Calendar.MONTH) + 1}.${
                model.date.get(Calendar.YEAR)
            }"
        }
    }

    class WasteModelDiff : DiffUtil.ItemCallback<WasteModel>() {
        override fun areItemsTheSame(oldItem: WasteModel, newItem: WasteModel): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: WasteModel, newItem: WasteModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteViewHolder {
        return WasteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.waste_list_item, parent, false)
                .let(WasteListItemBinding::bind)
        )
    }

    override fun onBindViewHolder(holder: WasteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}