package com.project.music.app.utils

fun String.IsValidEmail(): Boolean {
    if (!this.trim().isNullOrEmpty()) {
        return (android.util.Patterns.EMAIL_ADDRESS.matcher(this.trim())
            .matches()
                )
    } else return false
}