package cn.dong.viewbinder.sample.runtime

/**
 * @author dong on 2018/07/28.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentView(val layoutId: Int) {
}