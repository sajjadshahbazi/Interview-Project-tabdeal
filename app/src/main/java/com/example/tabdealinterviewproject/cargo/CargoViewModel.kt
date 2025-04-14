package com.example.tabdealinterviewproject.cargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.Result
import com.example.domain.model.CargoRepoModel
import com.example.domain.usecase.GetCargosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CargoViewModel @Inject constructor(private val getCargosUseCase: GetCargosUseCase) : ViewModel() {
    private val _cargoItems : MutableList<CargoRepoModel> = mutableListOf()
    var _selectedItem : CargoRepoModel? = null

    val cargoItems = MutableSharedFlow<List<CargoRepoModel>>()
    val selectedItem = MutableSharedFlow<CargoRepoModel?>()
    val _isLoading = MutableSharedFlow<Boolean>()
    val _error = MutableSharedFlow<String?>()
    var lockedItems :Boolean = false

    init {
        fetchCargoItems()
    }

    private fun fetchCargoItems() {
        viewModelScope.launch {
            _isLoading.emit(true)
            when (val result = getCargosUseCase()) {
                is Result.Success -> {
                    _cargoItems.clear()
                    _cargoItems.addAll(result.data.toList())
                    cargoItems.emit(_cargoItems)
                }
                is Result.Error -> {
                    _error.emit(result.exception.message ?: "An unexpected error occurred")
                }
            }
            _error.emit(null)
            _isLoading.emit(false)
        }
    }

    fun selectCargo(cargoRepoModel : CargoRepoModel) {
        if (!lockedItems) {
            lockedItems = true
            _selectedItem = cargoRepoModel
            _cargoItems.forEach { it.isSelected = ((_selectedItem?.id ?: -1) == (it.id)) }

            viewModelScope.launch {
                cargoItems.emit(_cargoItems)
                _selectedItem?.let {
                    selectedItem.emit(it)
                }
            }
        }
    }

    fun clearSelectedCargo() {
        lockedItems = false
        _selectedItem = null
        _cargoItems.forEach { it.isSelected = false }
        viewModelScope.launch {
            selectedItem.emit(_selectedItem)
            cargoItems.emit(_cargoItems)
        }
    }
}