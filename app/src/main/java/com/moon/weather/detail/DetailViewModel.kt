package com.moon.weather.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.network.Repo
import com.moon.network.data.Resource
import com.moon.weather.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repo: Repo
) : ViewModel(){

    private val _daysState = MutableStateFlow(HomeState())
    val daysState: StateFlow<HomeState> = _daysState.asStateFlow()

    fun getDays(city:String, day:String) {
        viewModelScope.launch {
            repo.getDays(city,day).collect { result ->
                when (result) {
                    is Resource.Error -> _daysState.value =
                        HomeState(error = "Unknown error")
                    is Resource.Loading -> _daysState.value = HomeState(isLoading = true)
                    is Resource.Success -> _daysState.value =
                        HomeState(weather = result.data)
                }
            }
        }
    }
}