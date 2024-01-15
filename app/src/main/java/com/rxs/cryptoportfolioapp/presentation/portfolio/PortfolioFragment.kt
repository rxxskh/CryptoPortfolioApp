package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.common.toRussianCurrency
import com.rxs.cryptoportfolioapp.common.toRussianFormat
import com.rxs.cryptoportfolioapp.common.toUsdtCurrency
import com.rxs.cryptoportfolioapp.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    @Inject
    lateinit var viewModel: PortfolioViewModel

    private lateinit var portfolioAssetsAdapter: PortfolioAssetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        portfolioAssetsAdapter = PortfolioAssetsAdapter()
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentPortfolio()
        startObserve()
    }

    private fun startObserve() {
        viewModel.portfolio.observe(viewLifecycleOwner) {
            binding.apply {
                tvFragmentPortfolioInvestedBalance.text = it.invRusBalance.toRussianCurrency()
                tvFragmentPortfolioInvestedUsdtBalance.text = it.invUsdtBalance.toUsdtCurrency()
                tvFragmentPortfolioInvestedRate.text =
                    "1 USDT = ${it.averageUsdt.toRussianCurrency()}"

                tvFragmentPortfolioCurrentBalance.text = it.curRusBalance.toRussianCurrency()
                tvFragmentPortfolioCurrentUsdtBalance.text = it.curUsdtBalance.toUsdtCurrency()
                tvFragmentPortfolioCurrentRate.text =
                    "1 USDT = ${it.usdtPrice.toRussianCurrency()}"

                if (it.rusProfitLoss >= 0) {
                    tvFragmentPortfolioCurrentDifferenceBad.visibility = View.GONE
                    tvFragmentPortfolioCurrentDifferenceBadPercent.visibility = View.GONE

                    tvFragmentPortfolioCurrentDifferenceGood.text = it.rusProfitLoss.toRussianCurrency()
                    tvFragmentPortfolioCurrentDifferenceGood.visibility = View.VISIBLE
                    tvFragmentPortfolioCurrentDifferenceGoodPercent.text = it.profitLossPercent
                    tvFragmentPortfolioCurrentDifferenceGoodPercent.visibility = View.VISIBLE
                } else {
                    tvFragmentPortfolioCurrentDifferenceGood.visibility = View.GONE
                    tvFragmentPortfolioCurrentDifferenceGoodPercent.visibility = View.GONE

                    tvFragmentPortfolioCurrentDifferenceBad.text = it.rusProfitLoss.toRussianCurrency()
                    tvFragmentPortfolioCurrentDifferenceBad.visibility = View.VISIBLE
                    tvFragmentPortfolioCurrentDifferenceBadPercent.text = it.profitLossPercent
                    tvFragmentPortfolioCurrentDifferenceBadPercent.visibility = View.VISIBLE
                }

                if (it.assets.size > 0) {
                    tvFragmentPortfolioEmptyPortfolioTitle.visibility = View.GONE
                    rvFragmentPortfolioAssets.visibility = View.VISIBLE
                    recreateAdapter()
                    portfolioAssetsAdapter.submitData(it.assets)
                } else {
                    rvFragmentPortfolioAssets.visibility = View.GONE
                    tvFragmentPortfolioEmptyPortfolioTitle.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            btnFragmentPortfolioInvest.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_portfolioFragment_to_investDialogFragment
                ).onClick(it)
            }

            btnFragmentPortfolioWithdraw.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_portfolioFragment_to_withdrawDialogFragment
                ).onClick(it)
            }

            rvFragmentPortfolioAssets.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@PortfolioFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = portfolioAssetsAdapter
            }
        }
    }

    private fun recreateAdapter() {
        portfolioAssetsAdapter = PortfolioAssetsAdapter()
        binding.rvFragmentPortfolioAssets.adapter = portfolioAssetsAdapter
    }
}

