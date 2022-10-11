package com.test.testtaskmyna.utilities

import android.app.Activity
import android.widget.Toast

fun showToast(activity: Activity, message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}