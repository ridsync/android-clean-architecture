package kr.clean.devoks.core.logging

import com.elvishew.xlog.formatter.border.BorderFormatter
import com.elvishew.xlog.internal.SystemCompat

/**
 * Created by devoks
 * Description :
 */
class OKBorderFormatter : BorderFormatter {
    override fun format(segments: Array<String>): String {
        if (segments.isEmpty()) {
            return ""
        }
        val nonNullSegments = arrayOfNulls<String>(segments.size)
        var nonNullCount = 0
        for (segment in segments) {
            if (segment != null) {
                nonNullSegments[nonNullCount++] = segment
            }
        }
        if (nonNullCount == 0) {
            return ""
        }
        val msgBuilder = StringBuilder()
        msgBuilder.append("Be Happy")
        msgBuilder.append(SystemCompat.lineSeparator)
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(SystemCompat.lineSeparator)
        for (i in 0 until nonNullCount) {
            val segment = nonNullSegments[i] ?: ""
            msgBuilder.append(appendVerticalBorder(segment))
            if (i != nonNullCount - 1) {
                msgBuilder.append(SystemCompat.lineSeparator).append(DIVIDER_HORIZONTAL_BORDER)
                    .append(SystemCompat.lineSeparator)
            } else {
                msgBuilder.append(SystemCompat.lineSeparator)
                    .append(BOTTOM_HORIZONTAL_BORDER)
            }
        }
        return msgBuilder.toString()
    }

    companion object {
        private const val VERTICAL_BORDER_CHAR = '║'

        // Length: 100.
        private const val TOP_HORIZONTAL_BORDER =
            "╔════════════════════════════════════════════════════" +
                    "═══════════════════════════════════════════════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════"

        // Length: 99.
        private const val DIVIDER_HORIZONTAL_BORDER =
            "╟─────────────────────────────────────────────────" +
                    "─────────────────────────────────────────────"

        // Length: 100.
        private const val BOTTOM_HORIZONTAL_BORDER =
            "╚════════════════════════════════════════════════════" +
                    "═══════════════════════════════════════════════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════"

        /**
         * Add {@value #VERTICAL_BORDER_CHAR} to each line of msg.
         *
         * @param msg the message to add border
         * @return the message with {@value #VERTICAL_BORDER_CHAR} in the start of each line
         */
        private fun appendVerticalBorder(msg: String): String {
            val borderedMsgBuilder = StringBuilder(msg.length + 10)
            val lines = msg.split(SystemCompat.lineSeparator.toRegex()).toTypedArray()
            var i = 0
            val N = lines.size
            while (i < N) {
                if (i != 0) {
                    borderedMsgBuilder.append(SystemCompat.lineSeparator)
                }
                val line = lines[i]
                borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(" ").append(line)
                i++
            }
            return borderedMsgBuilder.toString()
        }
    }
}