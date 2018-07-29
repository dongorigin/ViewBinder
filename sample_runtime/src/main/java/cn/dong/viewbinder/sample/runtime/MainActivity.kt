package cn.dong.viewbinder.sample.runtime

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

@ContentView(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hello.text = "hello"
    }
}
