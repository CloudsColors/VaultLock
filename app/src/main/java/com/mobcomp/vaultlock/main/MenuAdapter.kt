package com.mobcomp.vaultlock.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.TextItemViewHolder
import com.mobcomp.vaultlock.database.Note
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val noteText : TextView? = itemView.findViewById(R.id.title_note)
        val noteTitle : TextView? = itemView.findViewById(R.id.note_string)

        fun bind(item: Note) {
            val res = itemView.context.resources
            })
        }
    }
}