package cn.dong.viewbinder.provider

import android.view.View

/**
 * @author dong on 2018/07/29.
 */
interface Provider<T> {
    fun findView(source: T, resId: Int): View
}