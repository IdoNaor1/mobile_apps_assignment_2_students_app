package com.lessons.studentsapp.models


data class Student(
    val id: String,
    val name: String,
    val isPresent: Boolean,
    val avatarResourceId: Int
) {

    fun withToggledPresence(): Student {
        return copy(isPresent = !isPresent)
    }

    fun isValid(): Boolean {
        return name.isNotBlank() && id.isNotBlank()
    }
}

