package com.example.appcent_homeproject.data.service

import com.example.appcent_homeproject.data.model.PhotoList
import com.example.appcent_homeproject.utils.Constants.FOLDER_URL_CURIOSITY
import com.example.appcent_homeproject.utils.Constants.FOLDER_URL_OPPORTUNITY
import com.example.appcent_homeproject.utils.Constants.FOLDER_URL_SPIRIT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoListAPI {

    @GET(FOLDER_URL_CURIOSITY)
    fun getCuriosityPhotos(@Query("sol") sol:String): Single<PhotoList>

    @GET(FOLDER_URL_OPPORTUNITY)
    fun getOpportunityPhotos(@Query("sol") sol:String): Single<PhotoList>

    @GET(FOLDER_URL_SPIRIT)
    fun getSpiritPhotos(@Query("sol") sol:String): Single<PhotoList>
}