package com.example.traningcomposeapp.utils

import android.util.Patterns

object RegexUtils {
    val onlyAlphabetRegex = Regex("^[a-zA-Z]*$")
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}