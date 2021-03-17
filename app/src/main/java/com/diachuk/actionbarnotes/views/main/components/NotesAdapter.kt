package com.diachuk.actionbarnotes.views.main.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(var notes: List<NoteDTO>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var onEditClick: ((id: Int) -> Unit)? = null
    var onDeleteClick: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return NoteViewHolder(
            view,
            { onEditClick?.invoke(notes[pos].id) },
            { onDeleteClick?.invoke(notes[pos].id) })
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.title = notes[position].title
    }

    class NoteViewHolder(val view: View, onEdit: () -> Unit, onDelete: () -> Unit) :
        RecyclerView.ViewHolder(view) {
        var title: String = ""
            set(value) {
                field = value
                view.note_title.text = value
            }

        init {
            view.note_edit.setOnClickListener { onEdit() }
            view.note_remove.setOnClickListener { onDelete() }
        }
    }
}