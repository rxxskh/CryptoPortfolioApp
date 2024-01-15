package com.rxs.cryptoportfolioapp.presentation.new_asset

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.FragmentNewAssetBuyValueBinding
import com.rxs.cryptoportfolioapp.databinding.FragmentNewAssetSellValueBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewAssetSellValueFragment : Fragment() {

    private lateinit var binding: FragmentNewAssetSellValueBinding

    @Inject
    lateinit var viewModel: NewAssetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAssetSellValueBinding.inflate(layoutInflater)
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObserve()
    }

    private fun startObserve() {
        viewModel.newAssetCoin.observe(viewLifecycleOwner) {
            binding.apply {
                it.coin!!.apply {
                    tvFragmentNewAssetSellValueCoinName.text = name
                    tvFragmentNewAssetSellValueCoinSymbol.text = symbol
                    tvFragmentNewAssetSellValueCaption1.text = symbol
                }
                tvFragmentNewAssetSellValueBalance.text = viewModel.getCoinBalance()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            ivFragmentNewAssetSellValueBack.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_newAssetSellValueFragment_to_newAssetCoinFragment
                ).onClick(it)
            }

            btnFragmentNewAssetSellValueAddTrans.setOnClickListener {
                if (etFragmentNewAssetSellValueCount.text.isNotBlank()) {
                    viewModel.applyTransaction(
                        value = etFragmentNewAssetSellValueCount.text.toString().toDouble()
                    )

                    Navigation.createNavigateOnClickListener(
                        R.id.action_newAssetSellValueFragment_to_newAssetCoinFragment
                    ).onClick(it)
                } else {
                    tvFragmentNewAssetSellValueError.visibility = View.VISIBLE
                }
            }

            btnFragmentNewAssetSellValueBuy.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_newAssetSellValueFragment_to_newAssetBuyValueFragment
                ).onClick(it)
            }
        }
    }
}