package com.mobcomp.vaultlock.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.TextItemViewHolder
import com.mobcomp.vaultlock.database.Note
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]

        holder.textView.text = item.noteTitle


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView


        return TextItemViewHolder(view)
    }
}