package eu.warble.pjappkotlin.utils

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


fun Snackbar.setTextColor(color: Int): Snackbar {
    val tv = view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    tv.setTextColor(color)
    return this
}

fun Snackbar.setBackgroundColor(color: Int): Snackbar {
    this.view.setBackgroundColor(color)
    return this
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}