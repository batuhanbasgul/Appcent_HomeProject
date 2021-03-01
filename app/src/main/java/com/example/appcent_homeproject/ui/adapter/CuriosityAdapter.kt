package com.example.appcent_homeproject.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcent_homeproject.R
import com.example.appcent_homeproject.data.model.Photo
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.card_view.view.*

class CuriosityAdapter(
        private val photoList: ArrayList<Photo>,
        private val mContext: Context
) : RecyclerView.Adapter<CuriosityAdapter.CardViewHolder>()
{
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindUI(photo: Photo) {
            Glide.with(itemView.context).load(photo.img_src).into(itemView.imageViewCardView)
            itemView.imageViewCardView.setOnClickListener(View.OnClickListener {
                showAlertDialog(photo)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindUI(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    private fun showAlertDialog(photo:Photo){
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog,null);

        Glide.with(view.context).load(photo.img_src).into(view.imageViewAlertView)
        view.textViewCamera.text = photo.camera.full_name
        view.textViewLandingDate.text = photo.rover.landing_date
        view.textViewLaunchDate.text = photo.rover.launch_date
        view.textViewPicDate.text = photo.earth_date
        view.textViewRoverName.text = photo.rover.name
        view.textViewRoverStatus.text = photo.rover.status

        alertDialogBuilder.setView(view)
        alertDialogBuilder.create().show()
    }
}

