package com.example.appcent_homeproject.utils

object Constants {
    //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=dMWJpiM7yiN1iatxO9IErNVtPRQKQCNuForwzVxP
    const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
    const val FOLDER_URL_CURIOSITY = "curiosity/photos"
    const val FOLDER_URL_OPPORTUNITY = "opportunity/photos"
    const val FOLDER_URL_SPIRIT = "spirit/photos"
    const val API_KEY = "dMWJpiM7yiN1iatxO9IErNVtPRQKQCNuForwzVxP"

    const val CURIOSITY_SOL_LIMIT = "3043"
    const val OPPORTUNITY_SOL_LIMIT = "5111"
    const val SPIRIT_SOL_LIMIT = "2208"
}