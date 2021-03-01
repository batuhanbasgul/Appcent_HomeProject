package com.example.appcent_homeproject.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appcent_homeproject.data.model.PhotoList
import com.example.appcent_homeproject.data.repository.NetworkStatus
import com.example.appcent_homeproject.data.repository.PhotoListRepository
import io.reactivex.disposables.CompositeDisposable

class CuriosityViewModel(
        private val photoListRepository: PhotoListRepository
) :ViewModel()
{
    private val compositeDisposable = CompositeDisposable()
    val photoList : LiveData<PhotoList> by lazy {
        photoListRepository.fetchPhotoList(compositeDisposable)
    }
    val networkStatus : LiveData<NetworkStatus> by lazy{
        photoListRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}