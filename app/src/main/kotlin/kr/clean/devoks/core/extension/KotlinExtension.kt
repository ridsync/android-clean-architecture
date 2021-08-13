package kr.clean.devoks.core.extension

import kr.clean.devoks.core.logging.OKLogger.Logger

/**
 * Created by devoks
 * Description :
 */
inline fun <T: Any> guardLets(vararg elements: T?, closure: (List<T?>) -> Nothing): List<T> {
    return if (elements.all { it != null }) {
        elements.filterNotNull()
    } else {
        closure(elements.toList())
    }
}

inline fun <T: Any> guardLet(element: T?, closure: (T?) -> Nothing): T {
    return element ?: closure(element)
}

inline fun <T: Any> ifLets(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}

inline fun <T: Any> ifLet(element: T?, closure: (T) -> Unit) {
    if (element != null ) {
        closure(element)
    }
}

fun String.formatSafe(vararg args: Any?) : String {
    return try {
        String.format(format = this, args = args)
    } catch (e: Throwable) {
        Logger.e(e)
        this
    }
}