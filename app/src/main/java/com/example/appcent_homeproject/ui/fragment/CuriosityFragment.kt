package com.example.appcent_homeproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.appcent_homeproject.R
import com.example.appcent_homeproject.data.model.Photo
import com.example.appcent_homeproject.data.model.PhotoList
import com.example.appcent_homeproject.data.repository.NetworkStatus
import com.example.appcent_homeproject.data.repository.PhotoListRepository
import com.example.appcent_homeproject.data.service.PhotoListAPI
import com.example.appcent_homeproject.data.service.PhotoListClient
import com.example.appcent_homeproject.ui.activity.MainActivity
import com.example.appcent_homeproject.ui.adapter.CuriosityAdapter
import com.example.appcent_homeproject.utils.PhotoFilter
import com.example.appcent_homeproject.view_model.CuriosityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.curiosity_fragment.*

class CuriosityFragment(private val roverName: String, private val cameraName: String) : Fragment() {

    private lateinit var curiosityViewModel: CuriosityViewModel
    private lateinit var photoListRepository: PhotoListRepository
    private lateinit var curiosityAdapter: CuriosityAdapter
    private lateinit var fullList :ArrayList<Photo>             //it will be cleared when recreated on main activity by choosing camera

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        fullList = ArrayList()
        val view = inflater.inflate(R.layout.curiosity_fragment,container,false)
        val photoListAPI: PhotoListAPI = PhotoListClient.getClient()
        photoListRepository = PhotoListRepository(photoListAPI)
        curiosityViewModel=getViewModel()
        curiosityViewModel.photoList.observe(viewLifecycleOwner, Observer {
            for (p:Photo in it.photos){
                fullList.add(p)
            }
            bindUI()
        })
        curiosityViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            progressBarCuriosity.visibility = if (it== NetworkStatus.LOADING) View.VISIBLE else View.GONE
            textViewCuriosity.visibility = if (it== NetworkStatus.ERROR) View.VISIBLE else View.INVISIBLE
            textViewCuriosity.text=it.status.toString()
        })
        return view
    }

    private fun bindUI() {
        imageViewCuriosityNoImage.visibility=View.INVISIBLE
        fullList = PhotoFilter.filterList(fullList,roverName,cameraName)
        if(fullList.isEmpty()){
            imageViewCuriosityNoImage.visibility=View.VISIBLE
        }else{
            recyclerViewCuriosity.setHasFixedSize(true)
            recyclerViewCuriosity.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            curiosityAdapter=CuriosityAdapter(fullList,this.context!!)
            recyclerViewCuriosity.adapter=curiosityAdapter
        }
    }

    private fun getViewModel():CuriosityViewModel{
        return ViewModelProviders.of(this,object:ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass:Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return CuriosityViewModel(photoListRepository) as T
            }
        })[CuriosityViewModel::class.java]
    }
}