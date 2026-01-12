package com.lessons.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.models.Student
import com.lessons.studentsapp.utils.Constants

class AddStudentActivity : AppCompatActivity() {

    private lateinit var nameEt: EditText
    private lateinit var idEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var addressEt: EditText
    private lateinit var checkedCb: CheckBox
    private lateinit var cancelBtn: Button
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindViews()
        setupClicks()

    }

    private fun bindViews() {
        nameEt = findViewById(R.id.add_student_name)
        idEt = findViewById(R.id.add_student_id)
        phoneEt = findViewById(R.id.add_student_phone)
        addressEt = findViewById(R.id.add_student_address)
        checkedCb = findViewById(R.id.add_student_checked)

        cancelBtn = findViewById(R.id.add_student_cancel)
        saveBtn = findViewById(R.id.add_student_save)
    }

    private fun setupClicks() {
        cancelBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        saveBtn.setOnClickListener { saveStudent() }
    }

    private fun saveStudent() {
        val name = nameEt.text.toString().trim()
        val id = idEt.text.toString().trim()
        val phone = phoneEt.text.toString().trim()
        val address = addressEt.text.toString().trim()
        val isChecked = checkedCb.isChecked

        if (name.isBlank() || id.isBlank()) {
            Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            id = id,
            name = name,
            phone = phone,
            address = address,
            isChecked = isChecked,
            avatarResourceId = Constants.DEFAULT_AVATAR_RES_ID
        )

        try {
            Model.shared.addStudent(student)
            setResult(RESULT_OK)
            finish()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message ?: "Error", Toast.LENGTH_SHORT).show()
        }
    }


}