package cn.dong.viewbinder.provider

import android.app.Activity
import android.view.View

/**
 * @author dong on 2018/07/29.
 */
class ActivityProvider : Provider<Activity> {
    override fun findView(source: Activity, resId: Int): View {
        return source.findViewById(resId)
    }
}