package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lessons.studentsapp.models.Model

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    private var lastSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Model.shared.initializeSampleData(defaultAvatarId = R.drawable.student_avatar)

        recyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = StudentAdapter(
            students = Model.shared.getAllStudents(),
            onCheckedChanged = { studentId, position ->
                Model.shared.toggleChecked(studentId)
                adapter.submitList(Model.shared.getAllStudents())
                adapter.notifyItemChanged(position)
            }
        )

        recyclerView.adapter = adapter

        lastSize = Model.shared.getAllStudents().size

        val fab = findViewById<FloatingActionButton>(R.id.add_student_fab)
        fab.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        val newList = Model.shared.getAllStudents()
        val newSize = newList.size

        adapter.submitList(newList)

        if (newSize > lastSize) {
            adapter.notifyItemInserted(newSize - 1)
        }

        lastSize = newSize
    }

}