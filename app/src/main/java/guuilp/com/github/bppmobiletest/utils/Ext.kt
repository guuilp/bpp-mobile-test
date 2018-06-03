package guuilp.com.github.bppmobiletest.utils

import android.annotation.SuppressLint
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat


fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.NO_WRAP or Base64.URL_SAFE)
}

@SuppressLint("SimpleDateFormat")
fun String.formatDate() : String {

    val startTime = this
    // This could be MM/dd/yyyy, you original value is ambiguous
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val dateValue = input.parse(startTime)

    val format = SimpleDateFormat("dd/MM/yyyy - HH:mm")
    return format.format(dateValue)
}

fun Double.formatCurrency(currency: String) : String {
    val valueTwoDecimal = "%.2f".format(this)

    return when(currency){
        "BRL" -> "R$ $valueTwoDecimal"
        else -> valueTwoDecimal
    }
}