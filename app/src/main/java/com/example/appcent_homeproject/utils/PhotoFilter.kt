package com.example.appcent_homeproject.utils

import com.example.appcent_homeproject.data.model.Photo
import com.example.appcent_homeproject.data.model.PhotoList

class PhotoFilter {
    companion object{
        lateinit var filteredPhotoList: ArrayList<Photo>

        fun filterList(photoList: ArrayList<Photo>, roverName:String, cameraName:String):ArrayList<Photo>{
            filteredPhotoList = ArrayList()

            if(cameraName == "All"){
                for(photo:Photo in photoList){
                    if(photo.rover.name == roverName){
                        filteredPhotoList.add(photo)
                    }
                }
                return filteredPhotoList
            }else{
                for(photo:Photo in photoList){
                    if(photo.rover.name == roverName && photo.camera.full_name == cameraName){
                        filteredPhotoList.add(photo)
                    }
                }
            }
            return filteredPhotoList
        }
    }
}