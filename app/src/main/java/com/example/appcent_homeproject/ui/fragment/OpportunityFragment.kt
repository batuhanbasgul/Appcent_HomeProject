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
import com.example.appcent_homeproject.utils.PhotoFilter
import com.example.appcent_homeproject.view_model.CuriosityViewModel
import com.example.appcent_homeproject.view_model.OpportunityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.curiosity_fragment.*
import kotlinx.android.synthetic.main.opportunity_fragment.*

class OpportunityFragment(val roverName: String, val cameraName: String): Fragment() {

    private lateinit var opportunityViewModel: OpportunityViewModel
    private lateinit var photoListRepository: PhotoListRepository
    private lateinit var opportunityAdapter: OpportunityAdapter
    private lateinit var fullList :ArrayList<Photo>         //it will be cleared when recreated on main activity by choosing camera

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var i=0
        fullList = ArrayList()
        val view:View = inflater.inflate(R.layout.opportunity_fragment,container,false)
        val photoListAPI: PhotoListAPI = PhotoListClient.getClient()
        photoListRepository = PhotoListRepository(photoListAPI)
        opportunityViewModel=getViewModel()
        opportunityViewModel.photoList.observe(viewLifecycleOwner, Observer {
            for (p:Photo in it.photos){
                fullList.add(p)
            }
            bindUI()
        })
        opportunityViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            progressBarOpportunity.visibility = if (it== NetworkStatus.LOADING) View.VISIBLE else View.GONE
            textViewOpportunity.visibility = if (it== NetworkStatus.ERROR) View.VISIBLE else View.INVISIBLE
            textViewOpportunity.text=it.status.toString()
        })
        return view
    }

    private fun bindUI() {
        imageViewOpportunityNoImage.visibility=View.INVISIBLE
        fullList = PhotoFilter.filterList(fullList,roverName,cameraName)
        if(fullList.isEmpty()){
            imageViewOpportunityNoImage.visibility=View.VISIBLE
        }else{
            recyclerViewOpportunity.setHasFixedSize(true)
            recyclerViewOpportunity.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            opportunityAdapter=OpportunityAdapter(fullList,this.context!!)
            recyclerViewOpportunity.adapter=opportunityAdapter
        }
    }

    private fun getViewModel():OpportunityViewModel{
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass:Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return OpportunityViewModel(photoListRepository) as T
            }
        })[OpportunityViewModel::class.java]
    }
}