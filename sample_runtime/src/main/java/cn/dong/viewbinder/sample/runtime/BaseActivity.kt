package cn.dong.viewbinder.sample.runtime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author dong on 2018/07/28.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this::class.java.getAnnotation(ContentView::class.java)?.let { setContentView(it.layoutId) }
    }
}
