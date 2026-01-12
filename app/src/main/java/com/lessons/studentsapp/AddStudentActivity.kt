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

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEt = findViewById<EditText>(R.id.add_student_name)
        val idEt = findViewById<EditText>(R.id.add_student_id)
        val phoneEt = findViewById<EditText>(R.id.add_student_phone)
        val addressEt = findViewById<EditText>(R.id.add_student_address)
        val checkedCb = findViewById<CheckBox>(R.id.add_student_checked)

        val cancelBtn = findViewById<Button>(R.id.add_student_cancel)
        val saveBtn = findViewById<Button>(R.id.add_student_save)

        cancelBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        saveBtn.setOnClickListener {

            val name = nameEt.text.toString().trim()
            val id = idEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            val address = addressEt.text.toString().trim()
            val isChecked = checkedCb.isChecked

            if (name.isBlank() || id.isBlank()) {
                Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(
                id = id,
                name = name,
                phone = phone,
                address = address,
                isChecked = isChecked,
                avatarResourceId = R.drawable.student_avatar
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
}