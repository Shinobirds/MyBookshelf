package com.kunfei.bookshelf.widget.page

import androidx.core.util.rangeTo
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 章节
 */

class TxtChapter internal constructor(val position: Int) {
    val txtPageList = ArrayList<TxtPage>()
    val txtPageLengthList = ArrayList<Int>()
    val paragraphLengthList = ArrayList<Int>()
    var status = Status.LOADING
    var msg: String? = null

    val pageSize: Int
        get() = txtPageList.size

    fun addPage(txtPage: TxtPage) {
        txtPageList.add(txtPage)
    }

    fun getPage(page: Int): TxtPage? {
        return if (txtPageList.isNotEmpty()) {
            txtPageList[max(0, min(page, txtPageList.size - 1))]
        } else null
    }

    fun getPageLength(position: Int): Int {
        return if (position >= 0 && position < txtPageLengthList.size) {
            txtPageLengthList[position]
        } else -1
    }

    fun addTxtPageLength(length: Int) {
        txtPageLengthList.add(length)
    }

    fun addParagraphLength(length: Int) {
        paragraphLengthList.add(length)
    }

    fun getParagraphIndex(length: Int): Int {
        for (i in paragraphLengthList.indices) {
            if ((i == 0 || paragraphLengthList[i - 1] < length) && length <= paragraphLengthList[i]) {
                return i
            }
        }
        return -1
    }

    fun getPageIndexByCharCount(charCount: Int): Int {
        var index = 0
        var count = 0

        for (i in 0 until txtPageList.size) {
            index = i
            count += txtPageList[index].content.length
            if (count > charCount) return index
        }
        return index
    }

    fun getCharCountByPageIndex(pageIndex: Int): Int {
        var index: Int
        var count = 0

        for (i in 0 until txtPageList.size) {
            index = i
            if (index >= pageIndex) return count
            count += txtPageList[index].content.length
        }
        return count
    }

    enum class Status {
        LOADING, FINISH, ERROR, EMPTY, CATEGORY_EMPTY, CHANGE_SOURCE
    }
}
