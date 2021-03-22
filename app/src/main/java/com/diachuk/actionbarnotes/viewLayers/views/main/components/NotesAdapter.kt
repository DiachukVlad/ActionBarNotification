package com.diachuk.actionbarnotes.viewLayers.views.main.components

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.databinding.NoteLayoutBinding

class NotesAdapter(var notes: List<NoteDTO>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var onEditClick: ((id: Int) -> Unit)? = null
    var onDeleteClick: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        val binding = NoteLayoutBinding.bind(view)
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
        holder.content = notes[position].content
        holder.color = notes[position].color
    }

    class NoteViewHolder(
        private val binding: NoteLayoutBinding,
        view: View,
        onEdit: () -> Unit,
        onDelete: () -> Unit
    ) :
        RecyclerView.ViewHolder(view) {

        private var isCollapsed = true

        var title: String = ""
            set(value) {
                field = value
                binding.noteTitle.setText(value)
            }

        var content: String = ""
            set(value) {
                field = value
                binding.noteContent.setText(value)
            }

        var color: Int = ContextCompat.getColor(view.context, R.color.gray)
            set(value) {
                field = value
                (binding.root.background as GradientDrawable).setColor(value)
            }

        init {
            binding.apply {
//                noteEdit.setOnClickListener { onEdit() }
//                noteRemove.setOnClickListener { onDelete() }
                noteTitle.editable = !isCollapsed
                noteContent.editable = !isCollapsed
                root.setOnClickListener {
                    isCollapsed = !isCollapsed
                    noteTitle.editable = !isCollapsed
                    noteContent.editable = !isCollapsed
//                    extraSpace.isVisible = !isCollapsed
                }
            }
        }
    }
}