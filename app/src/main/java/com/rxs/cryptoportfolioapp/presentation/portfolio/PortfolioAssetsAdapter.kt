package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rxs.cryptoportfolioapp.common.toNewAssetBalance
import com.rxs.cryptoportfolioapp.common.toUsCurrency
import com.rxs.cryptoportfolioapp.common.toUsdtCurrency
import com.rxs.cryptoportfolioapp.data.shared_prefs.PortfolioCoin
import com.rxs.cryptoportfolioapp.databinding.ItemPortfolioAssetBinding

class PortfolioAssetsAdapter :
    RecyclerView.Adapter<PortfolioAssetsAdapter.PortfolioAssetViewHolder>() {

    private lateinit var binding: ItemPortfolioAssetBinding
    private var portfolioCoinsList = emptyList<PortfolioCoin>()


    inner class PortfolioAssetViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setData(portfolioCoin: PortfolioCoin, position: Int) = binding.apply {
            portfolioCoin.apply {
                tvItemPortfolioAssetPosition.text = (position + 1).toString()
                tvItemPortfolioAssetSymbol.text = coin?.symbol ?: ""
                tvItemPortfolioAssetValue.text = value.toNewAssetBalance()
                tvItemPortfolioAssetInvestedPrice.text = (investedPrice * value).toUsdtCurrency()
                tvItemPortfolioAssetCurrentPrice.text = (currentPrice * value).toUsCurrency()
                if (profitLoss >= 0) {
                    tvItemPortfolioAssetDifferenceBad.visibility = View.GONE
                    tvItemPortfolioAssetDifferenceGood.text = profitLoss.toUsdtCurrency()
                    tvItemPortfolioAssetDifferenceGood.visibility = View.VISIBLE
                } else {
                    tvItemPortfolioAssetDifferenceGood.visibility = View.GONE
                    tvItemPortfolioAssetDifferenceBad.text = profitLoss.toUsdtCurrency()
                    tvItemPortfolioAssetDifferenceBad.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioAssetViewHolder {
        binding =
            ItemPortfolioAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PortfolioAssetViewHolder()
    }

    override fun getItemCount(): Int {
        return portfolioCoinsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: PortfolioAssetViewHolder, position: Int) {
        holder.setData(portfolioCoin = portfolioCoinsList[position], position = position)
    }

    fun submitData(data: List<PortfolioCoin>) {
        portfolioCoinsList = data
    }
}