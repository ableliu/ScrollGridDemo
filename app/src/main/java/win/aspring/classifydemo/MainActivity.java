package win.aspring.classifydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import win.aspring.library.ScrollGridView;
import win.aspring.library.interf.OnClassifyClickListener;

public class MainActivity extends AppCompatActivity {
    private ScrollGridView mScrollGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mScrollGridView = findViewById(R.id.scroll_grid);
        mScrollGridView.setImageLoaderListener(new ImageLoader());

        String url = "http://chuantu.biz/t6/113/1509006827x2073382805.png";

        List<Classify> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Classify classify = new Classify();
            classify.setName("分类" + i);
            classify.setImagePath(url);
            list.add(classify);
        }

        mScrollGridView.setData(list, 2, 5);
        mScrollGridView.setOnClassifyClickListener(new OnClassifyClickListener() {
            @Override
            public void onClassifyClick(View view, int position) {
                Toast.makeText(MainActivity.this, "position：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
