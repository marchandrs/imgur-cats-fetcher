package com.stefanini.imgurcatfetcher.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanini.imgurcatfetcher.model.Image
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val images: MutableLiveData<List<Image>> by lazy {
        MutableLiveData<List<Image>>().also {
            loadImages()
        }
    }

    fun getImages(): LiveData<List<Image>> {
        return images
    }

    private fun loadImages() {
        // Do an asynchronous operation to fetch users.
        viewModelScope.launch {
            //
        }
    }
}