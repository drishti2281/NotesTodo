package com.drishti.notestodo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drishti.notestodo.databinding.AdapterNotesBinding

class Adapter(val context: Context,
              var arrayList: ArrayList<NotesEntity>,
              val ClickInterfaces:clickinterface): RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterNotesBinding?) :
        RecyclerView.ViewHolder(binding?.root ?: View(context)) {


        fun showData(position: Int) {
            val data=arrayList[position]
            binding?.tvnotes?.text =data.title
            binding?.tvdocument?.text =data.subtitle
            binding?.tvShowNotes?.text=data.notes
            binding?.deleteNotes?.setOnClickListener {

                ClickInterfaces.onDeleteListener(data)
            }
            binding?.clickItem?.setOnClickListener {
                ClickInterfaces.onClickListener(data)
            }
            binding?.shareNotes?.setOnClickListener{
                ClickInterfaces.onShareListener(data)
            }
        }

    }
    fun filteredList(userList: ArrayList<NotesEntity>){
        this.arrayList=userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterNotesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showData(position)

    }

    override fun getItemCount(): Int {

        return arrayList.size
    }

    fun refreshData(userList: ArrayList<NotesEntity>) {
        notifyDataSetChanged()
    }
              }