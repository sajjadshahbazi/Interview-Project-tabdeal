package com.example.tabdealinterviewproject.exchange

import androidx.lifecycle.ViewModel
import com.example.common.model.Result
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TokenRepoModel
import com.example.domain.usecase.GetTokensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AssetsViewModel @Inject constructor(private val getAssetsUseCase: GetTokensUseCase) :
    ViewModel() {

    val delayTime : Long = 30000
    val tokens = MutableSharedFlow<List<TokenRepoModel>>(replay = 0)
    val isLoading = MutableSharedFlow<Boolean>(replay = 0)
    val error = MutableSharedFlow<String?>(replay = 0)

    suspend fun startAssetLoadingLoop() {
        while (true) {
            loadAssets()
            delay(delayTime)
        }
    }

    fun loadAssets() {
        viewModelScope.launch {
            isLoading.emit(true)
            when (val result = getAssetsUseCase()) {
                is Result.Success -> {
                    tokens.emit(result.data.toList())
                    error.emit(null)
                }

                is Result.Error -> {
                    error.emit(result.exception.message ?: "An unexpected error occurred")
                }
            }
            isLoading.emit(false)
        }
    }
}