package kr.clean.devoks.core.extension

import android.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_SENDTO
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kr.clean.devoks.core.logging.OKLogger.Logger
import kr.clean.devoks.presentation.base.BaseFragment
import java.util.*

/**
 * Created by devoks
 * Description :
 * 1. Context,Resource Function
 * 2. View Funtion
 * 2. Activity,Fragment Class Extension
 * 3. Special API Extension (Permission,SMS...)
*/

/**
 * 특정 리소스 ID와 관련된 색상을 반환
 *  @param color 리소스 id
 *  return color
 */
fun Context.getColorCompat(colorId: Int) = ContextCompat.getColor(this, colorId)

/**
 * 특정 리소스 ID와 관련된 Drawable을 반환
 *  @param drawableId 리소스 id
 *  return drawable
 */
fun Context.getDrawableCompat(drawableId: Int) = ContextCompat.getDrawable(this, drawableId)

/**
 * 특정 리소스 ID와 관련된 색상을 반환(다중)
 *  @param colorId 리소스 id
 *  return colors
 */
fun Context.getColorStateLists(@ColorRes colorId: Int) = ContextCompat.getColorStateList(this, colorId)

/**
 * 특정 리소스 ID와 관련된 List<String>을 반환
 *  @param drawableId 리소스 id
 *  return drawable
 */
fun Context.getArrayValueList(@ArrayRes resId: Int) : List<String> {
    return try {
        resources.getStringArray(resId).toList()
    } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
        emptyList()
    }
}

fun Activity.sendEmail(vararg emailList: String, subject: String = "", text: String = ""): Boolean {
    val sendIntent: Intent = Intent().apply {
        action = ACTION_SEND
        putExtra(Intent.EXTRA_EMAIL, emailList)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    return try {
            startActivity(Intent.createChooser(sendIntent, subject))
            true
        } catch (ex: ActivityNotFoundException) {
            Logger.e(ex)
            false
        }
}

/**
 * SMS 보내기
 */
fun Context.sms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:" + phone)
    val intent = Intent(ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

/**
 * 전화걸기
 */
fun Context.dial(tel: String?) = startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel")))

/**
 * 브라우저 이동
 * @param url 주소
 * @param newTask flag
 *
 */
fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    val marketLaunch = Intent(Intent.ACTION_VIEW)
    if (newTask) marketLaunch.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    marketLaunch.data = Uri.parse(url)
    return try {
        startActivity(marketLaunch)
        true
    } catch (e: Throwable) {
        e.printStackTrace()
        false
    }

}

inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

inline val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

inline val Context.displayMetricks: DisplayMetrics
    get() = resources.displayMetrics

/**
 * DP로 픽셀 반환  // TODO 테스트 필요.
 * @param dip 픽셀값 구하기 위한 dip 수치
 * return int
 */
fun Context.dpToPx(dip: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip.toFloat(),
        resources.displayMetrics
    ).toInt()
}

/**
 * SP로 픽셀 반환
 * @param sp 픽셀값 구하기 위한 sp 수치
 * return int
 */
fun Context.spToPx(sp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        resources.displayMetrics
    ).toInt()
}


/**
 * View
 */
tailrec fun <T : View> View.findParent(parentType: Class<T>): T {
    return if (parent.javaClass == parentType) parent as T else (parent as View).findParent(
        parentType
    )
}

fun View.getString(stringResId: Int): String = resources.getString(stringResId)

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.setVisible(isShow: Boolean): View {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
    return this
}

fun View.toggleVisible(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
    return this
}

fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        clearFocus()
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun View.doOnLayout(onLayout: (View) -> Boolean) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View, left: Int, top: Int, right: Int, bottom: Int,
            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            if (onLayout(view)) {
                view.removeOnLayoutChangeListener(this)
            }
        }
    })
}

/**
 * 기본 SpannableText
 * @param fullText 전체 문자열 (필수)
 * @param targetText 변경할 문자열 (필수)
 * @param startIndex 변경할 문자열 시작위치
 * @param size 변경할 폰트 크기
 * @param color 변경할 폰트 색상
 * @param typeFace 변경할 폰트 스타일 (Normal, Bold,...)
 */
fun TextView.setSpannable(
    fullText: String = text.toString(),
    targetText: String,
    startIndex: Int = -1,
    sizeDp: Int = -1,
    color: Int = -1,
    typeFace: Int = Typeface.NORMAL,
    additionSpan: CharacterStyle? = null
) {
    setText(fullText, TextView.BufferType.SPANNABLE)
    val span = text as Spannable
    val startIdx = if (startIndex == -1) fullText.indexOf(targetText) else startIndex
    if (startIdx == -1) {
        Logger.e("[Spannable] Subtext not exist in FullText !!! startIdx = -1 return")
        return
    }

    // 폰트 크기
    if (sizeDp != -1) {
        val sizePixel = context.dpToPx(sizeDp)
        span.setSpan(
            AbsoluteSizeSpan(sizePixel),
            startIdx,
            startIdx + targetText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    // 폰트 색상
    if (color != -1) {
        span.setSpan(
            ForegroundColorSpan(color),
            startIdx,
            startIdx + targetText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    // 폰트 스타일
    span.setSpan(
        StyleSpan(typeFace),
        startIdx,
        startIdx + targetText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (additionSpan != null) {
        span.setSpan(
            additionSpan,
            startIdx,
            startIdx + targetText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 키패드 활성 감지 이벤트
 * @param keyboardObserver 키패드 활성 감지리스너
 */
fun BaseFragment<*>.setKeyboardVisibilityObserver(keyboardObserver: (Boolean) -> Unit) {
    val parentView = (activity?.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
    parentView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        private var alreadyOpen = false
        private val defaultKeyboardHeightDP = 100
        private val EstimatedKeyboardDP =
            defaultKeyboardHeightDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0
        private val rect = Rect()
        override fun onGlobalLayout() {
            val estimatedKeyboardHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                EstimatedKeyboardDP.toFloat(),
                parentView.resources.displayMetrics
            )
                .toInt()
            parentView.getWindowVisibleDisplayFrame(rect)
            val heightDiff = parentView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight
            if (isShown == alreadyOpen) {
                return
            }
            alreadyOpen = isShown
            keyboardObserver.invoke(isShown)
        }
    })
}