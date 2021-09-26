package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.getCurrentFocus()
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootview = this.window.decorView
    rootview.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootview.getHeight()
    val heightDifference = screenHeight - (r.bottom - r.top)
//    Log.d("M_MainActivity", "Rect ${r.bottom} ${r.top}" )
//    Log.d("M_MainActivity", "screenHeight $screenHeight" )
//    Log.d("M_MainActivity", "heightDifference $heightDifference" )
    return (heightDifference > 200)
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()