package com.randomgametpnv.help.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.randomgametpnv.help.R
import com.randomgametpnv.help.entities.JournalUiData

class JournalRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JournalUiData>() {

        override fun areItemsTheSame(oldItem: JournalUiData, newItem: JournalUiData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JournalUiData, newItem: JournalUiData): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return Holder(
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
            is Holder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<JournalUiData>) {
        differ.submitList(list)
    }

    class Holder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        private val eventTextView: TextView = itemView.findViewById(R.id.eventTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextVIew)

        fun bind(item: JournalUiData) = with(itemView) {

            eventTextView.text = item.text
            dateTextView.text = item.data

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: JournalUiData)
    }
}

