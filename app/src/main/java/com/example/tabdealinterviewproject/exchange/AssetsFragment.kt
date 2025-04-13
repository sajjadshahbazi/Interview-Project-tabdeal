package com.example.tabdealinterviewproject.exchange

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tabdealinterviewproject.R
import com.example.tabdealinterviewproject.databinding.FragmentAssetsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AssetsFragment : Fragment() {

    private val assetsViewModel: AssetsViewModel by viewModels()

    private var _binding: FragmentAssetsBinding? = null
    private val binding get() = _binding!!
    private lateinit var assetsAdapter: AssetsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var valueAmount: TextView
    private lateinit var currencyTommanButton: TextView
    private lateinit var currencyTetherButton: TextView
    private lateinit var assetsRecyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorMessageTextView: TextView
    private lateinit var showWholeOfAssets: ImageButton

    private var isFragmentVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        valueAmount = binding.tvValueAmount
        currencyTommanButton = binding.btnTommanCurrency
        currencyTetherButton = binding.btnTethterCurrency
        assetsRecyclerView = binding.rvAssetsRecyclerView
        loadingProgressBar = binding.prLoadingProgressBar
        errorMessageTextView = binding.tvErrorMessageTextView
        showWholeOfAssets = binding.ivShowWholeOfAssets

        assetsAdapter = AssetsAdapter()
        assetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        assetsRecyclerView.adapter = assetsAdapter

        swipeRefreshLayout.setOnRefreshListener {
            assetsViewModel.loadAssets()
        }

        valueAmount.setOnClickListener {
            val drawable: Drawable? = if (assetsAdapter.showCurrencyAmount) {
                assetsAdapter.showCurrencyAmount = false
                ContextCompat.getDrawable(showWholeOfAssets.context, R.drawable.ic_eye_closed)
            } else {
                assetsAdapter.showCurrencyAmount = true
                ContextCompat.getDrawable(showWholeOfAssets.context, R.drawable.ic_eye)
            }
            showWholeOfAssets.setImageDrawable(drawable)
            assetsAdapter.notifyDataSetChanged()
        }
        showWholeOfAssets.setOnClickListener {
            val drawable: Drawable? = if (assetsAdapter.showCurrencyAmount) {
                assetsAdapter.showCurrencyAmount = false
                ContextCompat.getDrawable(showWholeOfAssets.context, R.drawable.ic_eye_closed)
            } else {
                assetsAdapter.showCurrencyAmount = true
                ContextCompat.getDrawable(showWholeOfAssets.context, R.drawable.ic_eye)
            }
            showWholeOfAssets.setImageDrawable(drawable)
            assetsAdapter.notifyDataSetChanged()
        }

        currencyTetherButton.setOnClickListener {
            currencyTetherButton.background = ContextCompat.getDrawable(
                currencyTetherButton.context, R.drawable.active_button_background
            )
            currencyTommanButton.background = ContextCompat.getDrawable(
                currencyTetherButton.context, R.drawable.deactive_button_background
            )
            assetsAdapter.showTommanCurrencyAmount = false
            assetsAdapter.notifyDataSetChanged()
        }

        currencyTommanButton.setOnClickListener {
            currencyTetherButton.background = ContextCompat.getDrawable(
                currencyTetherButton.context, R.drawable.deactive_button_background
            )
            currencyTommanButton.background = ContextCompat.getDrawable(
                currencyTommanButton.context, R.drawable.active_button_background
            )
            assetsAdapter.showTommanCurrencyAmount = true
            assetsAdapter.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        isFragmentVisible = true
        observeViewModel()
        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.startAssetLoadingLoop()
        }
    }

    override fun onStop() {
        super.onStop()
        isFragmentVisible = false
        viewLifecycleOwner.lifecycleScope.coroutineContext.cancelChildren()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.tokens.collect { tokens ->
                if (isFragmentVisible) {
                    Toast.makeText(context, "refreshed -> items:${tokens.size}", Toast.LENGTH_SHORT).show()
                    assetsAdapter.submitList(tokens)
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.isLoading.collect { isLoading ->
                if (isFragmentVisible) {
                    loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.error.collect { error ->
                if (isFragmentVisible) {
                    errorMessageTextView.visibility =
                        if (error != null) View.VISIBLE else View.GONE
                    errorMessageTextView.text = error ?: ""
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}