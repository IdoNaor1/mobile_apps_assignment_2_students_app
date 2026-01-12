package com.lessons.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.models.Student
import com.lessons.studentsapp.utils.Constants

class EditStudentActivity : AppCompatActivity() {

    private lateinit var avatarIv: ImageView
    private lateinit var nameEt: EditText
    private lateinit var idEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var addressEt: EditText
    private lateinit var checkedCb: CheckBox
    private lateinit var deleteBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var saveBtn: Button

    private var originalStudentId: String? = null
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        originalStudentId = intent.getStringExtra(Constants.EXTRA_STUDENT_ID)

        if (originalStudentId == null) {
            Toast.makeText(this, "Student ID not provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        bindViews()
        setupClicks()
        loadStudent()
    }

    private fun bindViews() {
        avatarIv = findViewById(R.id.edit_student_avatar)
        nameEt = findViewById(R.id.edit_student_name)
        idEt = findViewById(R.id.edit_student_id)
        phoneEt = findViewById(R.id.edit_student_phone)
        addressEt = findViewById(R.id.edit_student_address)
        checkedCb = findViewById(R.id.edit_student_checked)
        deleteBtn = findViewById(R.id.edit_student_delete)
        cancelBtn = findViewById(R.id.edit_student_cancel)
        saveBtn = findViewById(R.id.edit_student_save)
    }

    private fun setupClicks() {
        cancelBtn.setOnClickListener {
            finish()
        }

        saveBtn.setOnClickListener {
            saveStudent()
        }

        deleteBtn.setOnClickListener {
            confirmDelete()
        }
    }

    private fun loadStudent() {
        originalStudentId?.let { id ->
            val student = Model.shared.getStudentById(id)
            if (student != null) {
                currentStudent = student
                displayStudent(student)
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displayStudent(student: Student) {
        avatarIv.setImageResource(student.avatarResourceId)
        nameEt.setText(student.name)
        idEt.setText(student.id)
        phoneEt.setText(student.phone)
        addressEt.setText(student.address)
        checkedCb.isChecked = student.isChecked
    }

    private fun saveStudent() {
        val name = nameEt.text.toString().trim()
        val newId = idEt.text.toString().trim()
        val phone = phoneEt.text.toString().trim()
        val address = addressEt.text.toString().trim()
        val isChecked = checkedCb.isChecked

        if (name.isBlank() || newId.isBlank()) {
            Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (newId != originalStudentId) {
            if (!Model.shared.isIdUnique(newId, originalStudentId)) {
                Toast.makeText(this, "Student with ID $newId already exists", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val updatedStudent = Student(
            id = newId,
            name = name,
            phone = phone,
            address = address,
            isChecked = isChecked,
            avatarResourceId = currentStudent?.avatarResourceId ?: Constants.DEFAULT_AVATAR_RES_ID
        )

        try {
            originalStudentId?.let { oldId ->
                Model.shared.updateStudent(oldId, updatedStudent)
                Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message ?: "Error updating student", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${currentStudent?.name}?")
            .setPositiveButton("Delete") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteStudent() {
        originalStudentId?.let { id ->
            val deleted = Model.shared.deleteStudent(id)
            if (deleted) {
                Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

