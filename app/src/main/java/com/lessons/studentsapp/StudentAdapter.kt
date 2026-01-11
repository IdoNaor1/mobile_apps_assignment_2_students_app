package com.lessons.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lessons.studentsapp.models.Student

class StudentAdapter(
    private var students: List<Student>,
    private val onCheckedChanged: (studentId: String, position: Int) -> Unit) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avatar: ImageView = itemView.findViewById(R.id.student_row_avatar)
        val name: TextView = itemView.findViewById(R.id.student_row_name)
        val id: TextView = itemView.findViewById(R.id.student_row_id)
        val checkbox: CheckBox = itemView.findViewById(R.id.student_row_checkbox)
    }

//    var listener: OnItemClickListener? = null

    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_row_layout, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.name.text = student.name
        holder.id.text = holder.itemView.context.getString(
            R.string.student_id_label,
            student.id
        )
        holder.avatar.setImageResource(student.avatarResourceId)

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = student.isChecked
        holder.checkbox.setOnCheckedChangeListener {_, _ ->
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onCheckedChanged(student.id, pos)
            }
        }

    }

    fun submitList(newStudents: List<Student>) {
        students = newStudents
    }


}