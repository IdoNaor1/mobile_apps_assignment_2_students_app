package com.lessons.studentsapp.models


data class Student(
    val id: String,
    val name: String,
    val phone: String = "",
    val address: String = "",
    val isChecked: Boolean = false,
    val avatarResourceId: Int
) {

    fun withToggledChecked(): Student {
        return copy(isChecked = !isChecked)
    }

    fun isValid(): Boolean {
        return name.isNotBlank() && id.isNotBlank()
    }
}

