package com.example.appcent_homeproject.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.appcent_homeproject.R
import com.example.appcent_homeproject.ui.fragment.CuriosityFragment
import com.example.appcent_homeproject.ui.fragment.OpportunityFragment
import com.example.appcent_homeproject.ui.fragment.SpiritFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val fragmentList:ArrayList<Fragment> = ArrayList()
    private val fragmentTitleList:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewPager(resources.getString(R.string.camera_0))
        setToolbar()
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setToolbarTitle()
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setToolbar(){
        setToolbarTitle()
        toolbarMain.setTitleTextColor(resources.getColor(R.color.white))
        toolbarMain.overflowIcon = resources.getDrawable(R.drawable.ic_baseline_cameraswitch_32)
        setSupportActionBar(toolbarMain)
    }

    private fun setToolbarTitle(){
        toolbarMain.title=fragmentTitleList[viewPager.currentItem]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_camera_0->{
                setViewPager(resources.getString(R.string.camera_0))
            }
            R.id.action_camera_1->{
                setViewPager(resources.getString(R.string.camera_1))
            }
            R.id.action_camera_2->{
                setViewPager(resources.getString(R.string.camera_2))
            }
            R.id.action_camera_3->{
                setViewPager(resources.getString(R.string.camera_3))
            }
            R.id.action_camera_4->{
                setViewPager(resources.getString(R.string.camera_4))
            }
            R.id.action_camera_5->{
                setViewPager(resources.getString(R.string.camera_5))
            }
            R.id.action_camera_6->{
                setViewPager(resources.getString(R.string.camera_6))
            }
            R.id.action_camera_7->{
                setViewPager(resources.getString(R.string.camera_7))
            }
            R.id.action_camera_8->{
                setViewPager(resources.getString(R.string.camera_8))
            }
            R.id.action_camera_9->{
                setViewPager(resources.getString(R.string.camera_9))
            }
            else->{
                setViewPager(resources.getString(R.string.camera_0))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    private fun setViewPager(cameraName:String) {
        fragmentList.clear()
        fragmentTitleList.clear()
        fragmentTitleList.add(resources.getString(R.string.vehicle_curiosity))
        fragmentTitleList.add(resources.getString(R.string.vehicle_opportunity))
        fragmentTitleList.add(resources.getString(R.string.vehicle_spirit))
        fragmentList.add(CuriosityFragment("Curiosity",cameraName))
        fragmentList.add(OpportunityFragment("Opportunity",cameraName))
        fragmentList.add(SpiritFragment("Spirit",cameraName))
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter=viewPagerAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position-> tab.text = fragmentTitleList[position]}.attach()
    }
}