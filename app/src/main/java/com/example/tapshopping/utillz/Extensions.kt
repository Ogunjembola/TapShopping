package com.example.tapshopping.utillz

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun <T> DataStore<Preferences>.get(
    key: Preferences.Key<T>,
    defaultValue: T
): T = runBlocking {
    data.first()[key] ?: defaultValue
}

fun <T> DataStore<Preferences>.set(
    key: Preferences.Key<T>,
    value: T?
) = runBlocking<Unit> {
    edit {
        if (value == null) {
            it.remove(key)
        } else {
            it[key] = value
        }
    }
}

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken,0)
}