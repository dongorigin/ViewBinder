package cn.dong.viewbinder.provider

import android.view.View

/**
 * @author dong on 2018/07/29.
 */
class ViewProvider : Provider<View> {
    override fun findView(source: View, resId: Int): View {
        return source.findViewById(resId)
    }
}