package com.randomgametpnv.help.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.randomgametpnv.help.R
import com.randomgametpnv.help.entities.Vote
import com.randomgametpnv.help.ui.HelpViewModel

class VotingPageAdapter(private val viewModel: HelpViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Vote>() {

        override fun areItemsTheSame(oldItem: Vote, newItem: Vote): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: Vote, newItem: Vote): Boolean { return oldItem == newItem }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VoteHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.vote_item,
                parent,
                false
            ),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VoteHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Vote>) {
        differ.submitList(list)
    }

    class VoteHolder
    constructor(
        itemView: View,
        private val viewModel: HelpViewModel
    ) : RecyclerView.ViewHolder(itemView) {

        private val voteText: TextView = itemView.findViewById(R.id.vote_subject)
        private val voteVariantList: ListView = itemView.findViewById(R.id.voteVariantList)

        fun bind(item: Vote) = with(itemView) {

            voteText.text = item.body
            with(voteVariantList) {

                //list adapter ------
                val listAdapter = object: BaseAdapter() {
                    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

                        val view = p1?: LayoutInflater.from(itemView.context).inflate(R.layout.vote_item_vatiant, null)
                        val button = view.findViewById<Button>(R.id.voteVariantButton)
                        val voteVariantText = view.findViewById<Button>(R.id.voteVariantText)
                        voteVariantText.text = item.voteVariant?.get(p0)?.variant

                        val voteVariantId: Int? = item.voteVariant?.get(p0)?.id
                        button.setOnClickListener {
                            voteVariantId?: return@setOnClickListener
                            viewModel.submitVotes(item.id, voteVariantId)
                        }
                        return view
                    }
                    override fun getItem(p0: Int) = item.voteVariant?.get(p0)
                    override fun getItemId(p0: Int) = p0.toLong()
                    override fun getCount() = item.voteVariant?.size?: 0
                }
                //------

                adapter = listAdapter
            }
        }
    }
}

