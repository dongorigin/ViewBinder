package cn.dong.viewbinder.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import cn.dong.viewbinder.ViewBinder;
import cn.dong.viewbinder.annotation.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.hello) TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewBinder.bind(this);
        helloText.setText("hello");
    }
}
