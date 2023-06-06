package com.moon.weather.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.network.Repo
import com.moon.network.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: Repo
) : ViewModel() {

    private val _cityState = MutableStateFlow(HomeState())
    val cityState: StateFlow<HomeState> = _cityState.asStateFlow()

    fun getCity(city:String) {
        viewModelScope.launch {
            repo(city).collect { result ->
                when (result) {
                    is Resource.Error -> _cityState.value =
                        HomeState(error = "Unknown error")

                    is Resource.Loading -> _cityState.value = HomeState(isLoading = true)
                    is Resource.Success -> _cityState.value =
                        HomeState(weather = result.data)
                }
            }
        }
    }
}