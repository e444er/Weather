package com.moon.weather.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.moon.weather.R
import com.moon.weather.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding by viewBinding(DetailFragmentBinding::bind)
    private val viewModel: DetailViewModel by viewModels()
    private val _adapter by lazy { DetailAdapter() }

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList()
        getSearch()
        binding.rv.isInvisible = false
        binding.week.setOnClickListener {
            viewModel.getDays(args.name, "7")
            binding.rv.isInvisible = true
        }
        binding.month.setOnClickListener {
            viewModel.getDays(args.name, "30")
            binding.rv.isInvisible = true
        }
    }

    private fun rvList() {
        binding.rv.apply {
            adapter = _adapter
            setHasFixedSize(true)
        }
    }

    private fun getSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.daysState.collectLatest { it ->
                if (it.isLoading) {
                    binding.rv.isVisible = false
                }
                if (it.error.isNotBlank()) {
                    binding.rv.isVisible = false
                }
                it.weather?.let {
                    binding.rv.isVisible = true
                    _adapter.differ.submitList(it.forecast.forecastday)
                }

            }
        }

    }
}