package com.example.appcent_homeproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.appcent_homeproject.ui.adapter.CuriosityAdapter
import com.example.appcent_homeproject.ui.adapter.OpportunityAdapter
import com.example.appcent_homeproject.ui.adapter.SpiritAdapter
import com.example.appcent_homeproject.utils.PhotoFilter
import com.example.appcent_homeproject.view_model.CuriosityViewModel
import com.example.appcent_homeproject.view_model.OpportunityViewModel
import com.example.appcent_homeproject.view_model.SpiritViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.curiosity_fragment.*
import kotlinx.android.synthetic.main.spirit_fragment.*

class SpiritFragment(val roverName: String, val cameraName: String) :Fragment() {

    private lateinit var spiritViewModel: SpiritViewModel
    private lateinit var photoListRepository: PhotoListRepository
    private lateinit var spiritAdapter : SpiritAdapter
    private lateinit var fullList :ArrayList<Photo>             //it will be cleared when recreated on main activity by choosing camera

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var i=0
        fullList = ArrayList()
        val view:View = inflater.inflate(R.layout.spirit_fragment,container,false)
        val photoListAPI: PhotoListAPI = PhotoListClient.getClient()
        photoListRepository = PhotoListRepository(photoListAPI)
        spiritViewModel=getViewModel()
        spiritViewModel.photoList.observe(viewLifecycleOwner, Observer {
            for (p:Photo in it.photos){
                fullList.add(p)
            }
            bindUI()
        })
        spiritViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            progressBarSpirit.visibility = if (it== NetworkStatus.LOADING) View.VISIBLE else View.GONE
            textViewSpirit.visibility = if (it== NetworkStatus.ERROR) View.VISIBLE else View.INVISIBLE
            textViewSpirit.text=it.status.toString()
        })
        return view
    }

    private fun bindUI() {
        imageViewSpiritNoImage.visibility=View.INVISIBLE
        fullList = PhotoFilter.filterList(fullList,roverName,cameraName)
        if(fullList.isEmpty()){
            imageViewSpiritNoImage.visibility=View.VISIBLE
        }else{
            recyclerViewSpirit.setHasFixedSize(true)
            recyclerViewSpirit.layoutManager=StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            spiritAdapter= SpiritAdapter(fullList,this.context!!)
            recyclerViewSpirit.adapter=spiritAdapter
        }
    }

    private fun getViewModel(): SpiritViewModel {
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass:Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return SpiritViewModel(photoListRepository) as T
            }
        })[SpiritViewModel::class.java]
    }
}