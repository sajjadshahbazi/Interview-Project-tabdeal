package com.example.tabdealinterviewproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TokenRepoModel

class AssetsAdapter :
    ListAdapter<TokenRepoModel, AssetsAdapter.AssetViewHolder>(AssetsDiffCallback()) {

    var showTommanCurrencyAmount: Boolean = true
    var showCurrencyAmount: Boolean = true

    class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val assetName: TextView = itemView.findViewById(R.id.tvAssetName)
        val assetNameFa: TextView = itemView.findViewById(R.id.tvAssetNameFa)
        val assetValue: TextView = itemView.findViewById(R.id.tvAssetAmount)
        val usdtValue: TextView = itemView.findViewById(R.id.tvUsdtValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val currentAsset = getItem(position)

        holder.assetName.text = currentAsset.currencyName
        holder.assetNameFa.text = currentAsset.currencyNameFa
        holder.usdtValue.text = "= ${showUnitAmount(currentAsset)}"
        holder.assetValue.text = if (!showCurrencyAmount) {
            "***"
        } else {
            currentAsset.credit
        }
    }

    fun showUnitAmount(tokenModel: TokenRepoModel): String = when {
        !showCurrencyAmount -> {
            "***"
        }

        showTommanCurrencyAmount -> {
            tokenModel.irtValue.toString()
        }

        else -> {
            tokenModel.usdtValue.toString()
        }
    }
}

class AssetsDiffCallback : DiffUtil.ItemCallback<TokenRepoModel>() {
    override fun areItemsTheSame(oldItem: TokenRepoModel, newItem: TokenRepoModel): Boolean {
        return oldItem.credit == newItem.credit
    }

    override fun areContentsTheSame(oldItem: TokenRepoModel, newItem: TokenRepoModel): Boolean {
        return oldItem == newItem
    }
}