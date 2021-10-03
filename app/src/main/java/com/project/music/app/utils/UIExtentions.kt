package com.project.music.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

/**
 * Extension [Context]: Dismiss Keyboard
 * @param view The View on Which it class
 */
fun Context.dismissKeyboard(view: View) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * Hide Action bar from UI
 */
fun AppCompatActivity.hideActionBar() {
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    supportActionBar?.hide()
}


/**
 * Activity intent without finish current activity & Without extra params
 */
fun <T> Context.navigateTo(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}

/**
 * Activity intent finish current activity & Without extra params
 */
fun <T> Context.navigateToAndfinshCurrent(activity: Activity, it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
    activity.finish()
}

/**
 * Extension [Context]: navigate To
 * @param it Giving Class
 */
fun <T> Context.navigateWithExtrasList(it: Class<T>, ketValue: List<Pair<String, String>>) {
    val intent = Intent(this, it)
    intent.apply {
        for (pair in ketValue) {
            putExtra(pair.first, pair.second)
        }

    }
    startActivity(intent)
}

/**
 * Extension [Context]: navigate To
 * @param it Giving Class
 * Finish current activity
 */
fun <T> Activity.navigateWithExtrasListFinishActivity(
    it: Class<T>,
    ketValue: List<Pair<String, String>>
) {
    val intent = Intent(this, it)
    intent.apply {
        for (pair in ketValue) {
            putExtra(pair.first, pair.second)
        }

    }
    startActivity(intent)
    this.finish()
}

fun <T> Activity.navigateWithExtrasListFinishActivity(
    it: Class<T>,
    ketValue: Pair<String, Int>
) {
    val intent = Intent(this, it)
    intent.apply { putExtra(ketValue.first, ketValue.second) }
    startActivity(intent)
    this.finish()
}

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}


/**
 * Extension [Context]: navigate To with giving Flags
 * @param it Giving Class
 */
fun <T> Activity.navigateToWithClearBackStackAndFinishCurrent(it: Class<T>) {
    val intent = Intent(this, it).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
    this.finish()
}

/**
 * VIEW Extension Fucntion to change visiblity to Gone.
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * VIEW Extension Fucntion to change visiblity to VIsible.
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun getScreenWidth(): Int {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
}
