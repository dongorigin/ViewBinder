package cn.dong.viewbinder

import cn.dong.viewbinder.provider.Provider

/**
 * @author dong on 2018/07/28.
 */
interface Binder<T> {
    fun bind(target: T, source: Any, provider: Provider<*>)
}
