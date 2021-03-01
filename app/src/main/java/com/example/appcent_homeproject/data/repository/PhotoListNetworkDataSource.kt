package com.example.appcent_homeproject.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appcent_homeproject.data.model.PhotoList
import com.example.appcent_homeproject.data.service.PhotoListAPI
import com.example.appcent_homeproject.utils.Constants.CURIOSITY_SOL_LIMIT
import com.example.appcent_homeproject.utils.Constants.OPPORTUNITY_SOL_LIMIT
import com.example.appcent_homeproject.utils.Constants.SPIRIT_SOL_LIMIT
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.random.Random

class PhotoListNetworkDataSource(
    private val photoListAPI: PhotoListAPI,
    private val compositeDisposable: CompositeDisposable
)
{
    private val _networkState = MutableLiveData<NetworkStatus>()
    private lateinit var solList:ArrayList<String>
    val networkStatus :LiveData<NetworkStatus>
        get()=_networkState

    private val _photoList = MutableLiveData<PhotoList>()
    val photoList :LiveData<PhotoList>
        get()=_photoList

    fun fetchCuriosityPhotoList(){
        generateRandomSolValue()
        _networkState.postValue(NetworkStatus.LOADING)
        try {
            compositeDisposable.addAll(
                    photoListAPI.getCuriosityPhotos("1000")
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                _photoList.postValue(it)
                                _networkState.postValue(NetworkStatus.LOADED)
                            },{
                                _networkState.postValue(NetworkStatus.ERROR)
                                Log.e("PhotosNetworkDataSource", it.message.toString())
                            }),
                    photoListAPI.getOpportunityPhotos("10")
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                _photoList.postValue(it)
                                _networkState.postValue(NetworkStatus.LOADED)
                            },{
                                _networkState.postValue(NetworkStatus.ERROR)
                                Log.e("PhotosNetworkDataSource", it.message.toString())
                            }),
                    photoListAPI.getSpiritPhotos("10")
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                _photoList.postValue(it)
                                _networkState.postValue(NetworkStatus.LOADED)
                            },{
                                _networkState.postValue(NetworkStatus.ERROR)
                                Log.e("PhotosNetworkDataSource", it.message.toString())
                            })
            )
        }catch (e:Exception){
            Log.e("PhotosNetworkDataSource", e.message.toString())
        }
    }

    private fun generateRandomSolValue(){
        solList = ArrayList()
        solList.add(Random.nextInt(0, CURIOSITY_SOL_LIMIT.toInt()).toString())
        solList.add(Random.nextInt(0, OPPORTUNITY_SOL_LIMIT.toInt()).toString())
        solList.add(Random.nextInt(0, SPIRIT_SOL_LIMIT.toInt()).toString())
    }

}