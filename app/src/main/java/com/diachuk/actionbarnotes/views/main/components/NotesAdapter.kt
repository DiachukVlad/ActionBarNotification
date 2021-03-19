package com.diachuk.actionbarnotes.views.main.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.databinding.NoteLayoutBinding

class NotesAdapter(var notes: List<NoteDTO>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var onEditClick: ((id: Int) -> Unit)? = null
    var onDeleteClick: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): NoteViewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return NoteViewHolder(
            binding,
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

    class NoteViewHolder(
        private val binding: NoteLayoutBinding,
        view: View,
        onEdit: () -> Unit,
        onDelete: () -> Unit
    ) :
        RecyclerView.ViewHolder(view) {
        var title: String = ""
            set(value) {
                field = value
                binding.noteTitle.text = value
            }

        init {
            binding.apply {
                noteEdit.setOnClickListener { onEdit() }
                noteRemove.setOnClickListener { onDelete() }
            }
        }
    }
}