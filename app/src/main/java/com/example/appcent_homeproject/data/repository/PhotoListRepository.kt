package com.example.appcent_homeproject.data.repository

import androidx.lifecycle.LiveData
import com.example.appcent_homeproject.data.model.PhotoList
import com.example.appcent_homeproject.data.service.PhotoListAPI
import io.reactivex.disposables.CompositeDisposable

class PhotoListRepository(private val photoListAPI: PhotoListAPI)
{
    lateinit var photoListNetworkDataSource: PhotoListNetworkDataSource

    fun fetchPhotoList(compositeDisposable: CompositeDisposable):LiveData<PhotoList>{
        photoListNetworkDataSource= PhotoListNetworkDataSource(photoListAPI,compositeDisposable)
        photoListNetworkDataSource.fetchCuriosityPhotoList()
        return photoListNetworkDataSource.photoList
    }

    fun getNetworkState():LiveData<NetworkStatus>{
        return photoListNetworkDataSource.networkStatus
    }
}