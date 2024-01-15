package com.rxs.cryptoportfolioapp.presentation.ranking_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rxs.cryptoportfolioapp.common.toRankingListPrice
import com.rxs.cryptoportfolioapp.databinding.ItemRankingListBinding
import com.rxs.cryptoportfolioapp.domain.model.Coin

class RankingListAdapter :
    RecyclerView.Adapter<RankingListAdapter.RankingListViewHolder>() {

    private lateinit var binding: ItemRankingListBinding
    private var coinsList = emptyList<Coin>()


    inner class RankingListViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setData(coin: Coin, position: Int) = binding.apply {
            tvItemRankingListPosition.text = (position + 1).toString()
            tvItemRankingListSymbol.text = coin.symbol
            tvItemRankingListPrice.text = coin.price.toRankingListPrice()

            if (coin.percentChange24 >= 0) {
                tvItemRankingListChange24Bad.visibility = View.GONE
                tvItemRankingListChange24Good.text = "${coin.percentChange24}%"
                tvItemRankingListChange24Good.visibility = View.VISIBLE
            } else {
                tvItemRankingListChange24Good.visibility = View.GONE
                tvItemRankingListChange24Bad.text = "${coin.percentChange24}%"
                tvItemRankingListChange24Bad.visibility = View.VISIBLE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingListViewHolder {
        binding = ItemRankingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingListViewHolder()
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

    override fun onBindViewHolder(holder: RankingListViewHolder, position: Int) {
        holder.setData(coin = coinsList[position], position = position)
    }

    fun submitData(data: List<Coin>) {
        coinsList = data
        notifyDataSetChanged()
    }
}