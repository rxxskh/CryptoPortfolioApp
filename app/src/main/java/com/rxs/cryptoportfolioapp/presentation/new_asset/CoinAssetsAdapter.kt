package com.rxs.cryptoportfolioapp.presentation.new_asset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.ItemNewAssetCoinBinding
import com.rxs.cryptoportfolioapp.domain.model.Coin

class CoinAssetsAdapter constructor(
    private val viewModel: NewAssetViewModel
) : RecyclerView.Adapter<CoinAssetsAdapter.CoinAssetViewHolder>() {

    private lateinit var binding: ItemNewAssetCoinBinding
    private var coinsList = emptyList<Coin>()


    inner class CoinAssetViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setData(coin: Coin) = binding.apply {
            tvItemNewAssetCoinName.text = coin.name
            tvItemNewAssetCoinSymbol.text = coin.symbol
            clItemNewAssetCoin.setOnClickListener {
                viewModel.selectCoin(coin = coin)
                Navigation.createNavigateOnClickListener(
                    R.id.action_newAssetCoinFragment_to_newAssetBuyValueFragment
                ).onClick(it)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinAssetViewHolder {
        binding =
            ItemNewAssetCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinAssetViewHolder()
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CoinAssetViewHolder, position: Int) {
        holder.setData(coin = coinsList[position])
    }

    fun submitData(data: List<Coin>) {
        coinsList = data
    }
}