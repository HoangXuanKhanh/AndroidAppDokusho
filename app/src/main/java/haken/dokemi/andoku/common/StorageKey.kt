package haken.dokemi.andoku.common

import android.content.SharedPreferences
import com.google.gson.Gson

enum class StorageKey(val key: String) {
    Token("local.token")
}

class StorageService(val sharedPreferences: SharedPreferences){
    fun <T> save(value: T?, forKey: StorageKey) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor.remove(forKey.key)
            editor.apply()
            return
        }

        when (value) {
            is String -> editor.putString(forKey.key, value)
            is Int -> editor.putInt(forKey.key, value)
            is Boolean -> editor.putBoolean(forKey.key, value)
            is Float -> editor.putFloat(forKey.key, value)
            is Long -> editor.putLong(forKey.key, value)
            else -> {
                val json = Gson().toJson(value)
                editor.putString(forKey.key, json)
            }
        }
        editor.apply()
    }

    inline fun <reified T> value(forKey: StorageKey): T? = sharedPreferences[forKey.key]

    fun string(forKey: StorageKey): String? = sharedPreferences[forKey.key]
    fun bool(forKey: StorageKey): Boolean = sharedPreferences[forKey.key] ?: false

    var token: String?
        get() = string(StorageKey.Token)
        set(value) = save(value, StorageKey.Token)

    companion object {

        var instance: StorageService? = null

        fun create(preferences: SharedPreferences) {
            val service = StorageService(preferences)
            instance = service
        }
    }
}


inline operator fun <reified T> SharedPreferences.get(key: String): T? {
    val result: Any? = when (T::class) {
        String::class -> getString(key, null)
        Boolean::class -> getBoolean(key, false)
        Int::class -> getInt(key, Int.MIN_VALUE)
        Float::class -> getFloat(key, Float.NEGATIVE_INFINITY)
        Long::class -> getLong(key, Long.MIN_VALUE)
        else -> getString(key, null)?.let {
            Gson().fromJson(it, T::class.java)
        }
    }

    return result as? T
}
