package com.mycompany.learnretrofitkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    context: Context?,
    _entityList: List<Entity?>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val entityList: List<Entity?>
    private val inflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val entity = entityList[position]
        val age: Int = entity!!.getAge()

        holder.textViewName.setText(entity.getName())
        holder.textViewAge.text = age.toString()
        holder.textViewPosition.setText(entity.getPosition())
    }

    override fun getItemCount(): Int {
        return entityList.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textViewName: TextView
        var textViewAge: TextView
        var textViewPosition: TextView
        var cardItem: CardView

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            textViewName = itemView.findViewById(R.id.recycler_text_view_name)
            textViewAge = itemView.findViewById(R.id.recycler_text_view_age)
            textViewPosition = itemView.findViewById(R.id.recycler_text_view_position)
            cardItem = itemView.findViewById(R.id.card_item)
            cardItem.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    init {
        inflater = LayoutInflater.from(context)
        entityList = _entityList
    }
}