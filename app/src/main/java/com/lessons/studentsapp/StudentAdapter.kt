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
    private val onCheckedChanged: (studentId: String, position: Int) -> Unit,
    private val onStudentClicked: ((studentId: String) -> Unit)? = null
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.student_row_avatar)
        private val name: TextView = itemView.findViewById(R.id.student_row_name)
        private val id: TextView = itemView.findViewById(R.id.student_row_id)
        private val checkbox: CheckBox = itemView.findViewById(R.id.student_row_checkbox)

        fun bind(
            student: Student,
            onCheckedChanged: (studentId: String, position: Int) -> Unit,
            onStudentClicked: ((studentId: String) -> Unit)?
        ) {
            name.text = student.name
            id.text = itemView.context.getString(R.string.student_id_label, student.id)
            avatar.setImageResource(student.avatarResourceId)

            // prevent recycled listeners from firing
            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = student.isChecked

            checkbox.setOnCheckedChangeListener { _, _ ->
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onCheckedChanged(student.id, pos)
                }
            }

            itemView.setOnClickListener {
                onStudentClicked?.invoke(student.id)
            }
        }
    }

//    var listener: OnItemClickListener? = null

    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_row_layout, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position], onCheckedChanged, onStudentClicked)
    }

    fun submitList(newStudents: List<Student>) {
        students = newStudents
    }


}