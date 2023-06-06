package com.moon.weather.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.moon.network.data.model.Weather
import com.moon.weather.R
import com.moon.weather.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding by viewBinding(HomeFragmentBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var name: String

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constraintLayout.setOnClickListener {
            val nav = HomeFragmentDirections.actionHomeFragmentToDetailFragment(name)
            findNavController().navigate(nav)
        }

        binding.editText.textChangeFlow()
            .debounce(1000)
            .distinctUntilChanged()
            .mapLatest { viewModel.getCity(it) }
            .flowOn(Dispatchers.IO)
            .catch { Toast.makeText(requireContext(), "Search", Toast.LENGTH_LONG).show() }
            .onEach { Log.d("onEach", "onEach is : $it") }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cityState.collectLatest {
                if (it.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.grad.visibility = View.GONE
                } else if (it.error.isNotEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    binding.grad.visibility = View.GONE
                    Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.grad.visibility = View.VISIBLE
                    Ui(it.weather)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun Ui(weather: Weather?) {
        with(binding) {
            name = weather?.location?.name.toString()
            condition.text = weather?.current?.condition?.text
            city.text = weather?.location?.name
            time.text = weather?.location?.localtime
            grad.text = weather?.current?.temp_c.toString() + " \u2103"

//                Glide.with(root)
//                    .load(weather?.current?.condition?.icon)
//                    .error(R.drawable.ic_launcher_background)
//                    .into(imageView)
        }
    }
}