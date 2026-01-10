package com.lessons.studentsapp.models

class Model private constructor() {

    private val students: MutableList<Student> = mutableListOf()

    companion object {
        val shared = Model()
    }


    fun initializeSampleData(defaultAvatarId: Int) {
        if (students.isEmpty()) {
            students.add(Student("123456", "John Doe", false, defaultAvatarId))
            students.add(Student("234567", "Jane Smith", true, defaultAvatarId))
            students.add(Student("345678", "Bob Johnson", false, defaultAvatarId))
            students.add(Student("456789", "Alice Williams", true, defaultAvatarId))
            students.add(Student("567890", "Charlie Brown", false, defaultAvatarId))
        }
    }

    fun getAllStudents(): List<Student> {
        return students.toList()
    }


    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student) {
        if (!student.isValid()) {
            throw IllegalArgumentException("Student data is invalid")
        }
        if (students.any { it.id == student.id }) {
            throw IllegalArgumentException("Student with ID ${student.id} already exists")
        }
        students.add(student)
    }


    fun updateStudent(oldId: String, updatedStudent: Student) {
        if (!updatedStudent.isValid()) {
            throw IllegalArgumentException("Student data is invalid")
        }

        val oldStudentIndex = students.indexOfFirst { it.id == oldId }
        if (oldStudentIndex == -1) {
            throw IllegalArgumentException("Student with ID $oldId not found")
        }

        // Check if new ID conflicts with another student (not the current one)
        if (oldId != updatedStudent.id && students.any { it.id == updatedStudent.id }) {
            throw IllegalArgumentException("Student with ID ${updatedStudent.id} already exists")
        }

        // Update the student
        students[oldStudentIndex] = updatedStudent
    }

    fun deleteStudent(id: String): Boolean {
        return students.removeIf { it.id == id }
    }

    fun togglePresence(id: String): Boolean {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = students[index].withToggledPresence()
            return true
        }
        return false
    }


    fun isIdUnique(id: String, excludeId: String? = null): Boolean {
        return students.none { it.id == id && it.id != excludeId }
    }
}

