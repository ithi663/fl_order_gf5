package com.randomgametpnv.cameras.ui.adapter

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.entities.CameraData
import com.randomgametpnv.cameras.ggets
import kotlinx.android.synthetic.main.all_cameras_rv_item.view.*

class AllCamerasRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CameraData>() {

        override fun areItemsTheSame(oldItem: CameraData, newItem: CameraData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CameraData, newItem: CameraData): Boolean {
            return oldItem.imageUpdateTime == newItem.imageUpdateTime
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.all_cameras_rv_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Holder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CameraData>) {
        differ.submitList(list)
    }

    class Holder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: CameraData) = with(itemView) {


            //imageView.setImageResource(R.drawable.dvor)

            val requestOp = RequestOptions.frameOf(100)

            val bb1 = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov")

            Glide.with(itemView.context).load(bb1).apply(requestOp).centerCrop().into(img)

            itemView.setOnClickListener {

                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CameraData)
    }
}

