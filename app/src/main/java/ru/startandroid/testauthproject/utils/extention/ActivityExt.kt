package ru.startandroid.testauthproject.utils.extention

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.startandroid.testauthproject.R
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val windowToken = currentFocus?.windowToken
    if (windowToken != null) {
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Activity.extraString(key: String? = null, def: String? = null) =
        delegate(key, def, { keyValue, defValue ->
            intent?.extras?.getString(keyValue, defValue)
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

fun Activity.extraInt(key: String? = null, def: Int = 0) =
        delegate(key, def, { keyValue, defValue ->
            intent?.extras?.getInt(keyValue, defValue)
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

fun Activity.extraBoolean(key: String? = null, def: Boolean = false) =
        nonNullDelegate(key, def, { keyValue, defValue ->
            intent?.extras?.getBoolean(keyValue, defValue) ?: defValue
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

fun <T: Enum<*>> Activity.extraEnum(key: String? = null, def: T) =
        nonNullDelegate(key, def, { keyValue, defValue ->
            (intent?.extras?.getSerializable(keyValue) as? T) ?: defValue
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

fun <T: Parcelable> Activity.extraParcelable(key: String? = null, def: T? = null) =
        delegate(key, def, { keyValue, defValue ->
            intent?.extras?.getParcelable(keyValue) ?: defValue
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

fun <T: Serializable> Activity.extraSerializable(key: String? = null, def: T? = null) =
        delegate(key, def, { keyValue, defValue ->
            (intent?.extras?.getSerializable(keyValue) as? T) ?: defValue
        }, { keyValue, value ->
            intent?.putExtra(keyValue, value)
        })

private inline fun <T> Activity.delegate(
        key: String?,
        defaultValue: T,
        crossinline getter: Activity.(String, T) -> T?,
        crossinline setter: Activity.(String, T?) -> Unit
): ReadWriteProperty<Any, T?> {
    return object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
                getter(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) =
                setter(key ?: property.name, value)
    }
}

private inline fun <T> Activity.nonNullDelegate(
        key: String?,
        defaultValue: T,
        crossinline getter: Activity.(String, T) -> T,
        crossinline setter: Activity.(String, T?) -> Unit
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
                getter(key ?: property.name, defaultValue) ?: defaultValue

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                setter(key ?: property.name, value)
    }
}

fun Intent.withArguments(vararg params: Pair<String, Any?>): Intent {
    for ((key, value) in params) {
        if (value != null) {
            when (value) {
                is Boolean -> putExtra(key, value)
                is Byte -> putExtra(key, value)
                is Char -> putExtra(key, value)
                is Short -> putExtra(key, value)
                is Int -> putExtra(key, value)
                is Long -> putExtra(key, value)
                is Float -> putExtra(key, value)
                is Double -> putExtra(key, value)
                is String -> putExtra(key, value)
                is CharSequence -> putExtra(key, value)
                is Parcelable -> putExtra(key, value)
                is Serializable -> putExtra(key, value)
                is BooleanArray -> putExtra(key, value)
                is ByteArray -> putExtra(key, value)
                is CharArray -> putExtra(key, value)
                is DoubleArray -> putExtra(key, value)
                is FloatArray -> putExtra(key, value)
                is IntArray -> putExtra(key, value)
                is LongArray -> putExtra(key, value)
                is Array<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    when {
                        value.isArrayOf<Parcelable>() -> putExtra(key, value as Array<out Parcelable>)
                        value.isArrayOf<CharSequence>() -> putExtra(key, value as Array<out CharSequence>)
                        value.isArrayOf<String>() -> putExtra(key, value as Array<out String>)
                        else -> throw IllegalArgumentException("Unsupported bundle component (${value::class})")
                    }
                }
                is ShortArray -> putExtra(key, value)
                is Bundle -> putExtra(key, value)
                null -> {/* ignore */}
                else -> throw IllegalArgumentException("Unsupported bundle component (${value::class})")
            }
        }
    }
    return this
}


fun Activity.getDisplayPoint(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val outPoint = Point()
    display.getSize(outPoint)
    return outPoint
}

fun FragmentManager.replaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean,
    needAnimate: Boolean
) {
    var ft = this.beginTransaction()
    val fragmentName = fragment.javaClass.simpleName
    if (addToBackStack) ft = ft.addToBackStack(fragmentName)
    if (needAnimate) ft.setCustomAnimations(
        R.animator.slide_in_left,
        R.animator.slide_out_right,
        R.animator.pop_out_right,
        R.animator.pop_in_left
    )
    ft.replace(containerViewId, fragment, fragmentName).commit()
}

fun Activity.showToast(text: Any) = Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()
fun Activity.showSnack(text: String) =
    Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()

fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, message, length).show()

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}