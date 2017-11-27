package com.aliouswang.swipeback;

import android.app.Activity;
import android.os.Bundle;

import com.rjsoft.swipelibrary.widget.CommonSwipeLayout;


/**
 * Created by admin on 2017/11/21.
 */

public class CommonSwipeGSYVideoActivity extends Activity {

    private CommonSwipeLayout swipe_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_swipe_layout);

        swipe_layout = findViewById(R.id.swipe_layout);
        swipe_layout.setFinishPercent(0.75f);
        swipe_layout.setOnFinishScroll(new CommonSwipeLayout.OnFinishScroll() {
            @Override
            public void complete() {
                finish();
            }
        });
    }
}
