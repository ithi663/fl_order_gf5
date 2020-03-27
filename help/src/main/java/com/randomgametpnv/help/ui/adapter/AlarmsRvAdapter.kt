package com.randomgametpnv.help.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.randomgametpnv.help.R
import com.randomgametpnv.help.entities.AlarmUiData
import kotlinx.android.synthetic.main.jornal_rv_item.view.*

class AlarmsRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlarmUiData>() {

        override fun areItemsTheSame(oldItem: AlarmUiData, newItem: AlarmUiData): Boolean {
            return oldItem.alarmDate == newItem.alarmDate
        }

        override fun areContentsTheSame(oldItem: AlarmUiData, newItem: AlarmUiData): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AlarmRvHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.jornal_rv_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlarmRvHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<AlarmUiData>) {
        differ.submitList(list)
    }

    class AlarmRvHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        private val alarmText: TextView = itemView.findViewById(R.id.eventTextView)
        private val alarmDate: TextView = itemView.findViewById(R.id.dateTextVIew)

        fun bind(item: AlarmUiData) = with(itemView) {

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            alarmText.text = item.alarmText
            alarmDate.text = item.alarmDate
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: AlarmUiData)
    }
}

