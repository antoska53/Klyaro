package ru.myacademyhomework.tinkoff.gif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myacademyhomework.tinkoff.ErrorLoad
import ru.myacademyhomework.tinkoff.LoadState
import ru.myacademyhomework.tinkoff.Loading
import ru.myacademyhomework.tinkoff.SuccessLoad
import ru.myacademyhomework.tinkoff.model.GifModel
import ru.myacademyhomework.tinkoff.network.RetrofitModule

class GifViewModel : ViewModel() {
    private val developersLifeApi = RetrofitModule.developersLifeApi
    private val _btnEnabledLiveData = MutableLiveData<Boolean>()
    val btnEnabledLiveData = _btnEnabledLiveData
    private val _loadStateLiveData = MutableLiveData<LoadState>()
    val loadStateLiveData = _loadStateLiveData
    private var listGifModel = arrayListOf<GifModel>()
    private var iter = listGifModel.listIterator()
    private val _gifLiveData = MutableLiveData<GifModel>()
    val gifLiveData: LiveData<GifModel> = _gifLiveData

    init {
        getNextGif()
    }

    private suspend fun loadGif() {
        _loadStateLiveData.value = Loading()
        try {
            _btnEnabledLiveData.value = iter.hasPrevious()
            val gifModel = developersLifeApi.getRandomImage()
            iter.add(gifModel)
            _gifLiveData.value = gifModel
            _loadStateLiveData.value = SuccessLoad()

        } catch (ex: Exception) {
            _loadStateLiveData.value = ErrorLoad()
        }
    }

    fun getNextGif() {
        if (iter.hasNext()) {
            val gifModel = iter.next()
            if (_gifLiveData.value == gifModel) {
                if (iter.hasNext()) {
                    _gifLiveData.value = iter.next()
                    _btnEnabledLiveData.value = iter.hasPrevious()
                } else {
                    viewModelScope.launch {
                        loadGif()
                    }
                }
            } else {
                _gifLiveData.value = gifModel
                _btnEnabledLiveData.value = iter.hasPrevious()
            }
        } else {
            viewModelScope.launch {
                loadGif()
            }
        }
    }

    fun getPrevGif() {
        if (_loadStateLiveData.value is ErrorLoad) {
            _loadStateLiveData.value = SuccessLoad()
        } else {
            if (iter.hasPrevious()) {
                var gifModel = iter.previous()
                if (_gifLiveData.value == gifModel && iter.hasPrevious()) {
                    _gifLiveData.value = iter.previous()
                    _loadStateLiveData.value = SuccessLoad()
                    _btnEnabledLiveData.value = iter.hasPrevious()
                } else {
                    _gifLiveData.value = gifModel
                    _loadStateLiveData.value = SuccessLoad()
                    _btnEnabledLiveData.value = iter.hasPrevious()
                }
            }
        }
    }
}