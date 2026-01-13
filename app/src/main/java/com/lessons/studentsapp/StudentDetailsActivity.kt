package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.models.Student
import com.lessons.studentsapp.utils.Constants

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var avatarIv: ImageView
    private lateinit var nameTv: TextView
    private lateinit var idTv: TextView
    private lateinit var phoneTv: TextView
    private lateinit var addressTv: TextView
    private lateinit var checkedTv: TextView
    private lateinit var editBtn: Button

    private lateinit var backBtn: Button


    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        studentId = intent.getStringExtra(Constants.EXTRA_STUDENT_ID)

        if (studentId == null) {
            Toast.makeText(this, "Student ID not provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        bindViews()
        setupClicks()
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun bindViews() {
        avatarIv = findViewById(R.id.student_details_avatar)
        nameTv = findViewById(R.id.student_details_name)
        idTv = findViewById(R.id.student_details_id)
        phoneTv = findViewById(R.id.student_details_phone)
        addressTv = findViewById(R.id.student_details_address)
        checkedTv = findViewById(R.id.student_details_checked)
        editBtn = findViewById(R.id.student_details_edit)
        backBtn = findViewById(R.id.student_details_back)
    }

    private fun setupClicks() {
        editBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(Constants.EXTRA_STUDENT_ID, studentId)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun loadStudent() {
        studentId?.let { id ->
            val student = Model.shared.getStudentById(id)
            if (student != null) {
                displayStudent(student)
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displayStudent(student: Student) {
        avatarIv.setImageResource(student.avatarResourceId)
        nameTv.text = student.name
        idTv.text = student.id
        phoneTv.text = student.phone.ifBlank { "N/A" }
        addressTv.text = student.address.ifBlank { "N/A" }
        checkedTv.text = if (student.isChecked) "✓" else "✗"
    }
}

