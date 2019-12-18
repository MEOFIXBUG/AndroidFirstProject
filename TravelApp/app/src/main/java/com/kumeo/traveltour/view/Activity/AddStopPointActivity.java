package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kumeo.traveltour.R;

public class AddStopPointActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_point);

        //config pop-up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double pop_up_width =dm.widthPixels*0.9;
        double pop_up_height=dm.heightPixels*0.9;

        getWindow().setLayout((int)pop_up_width, (int)pop_up_height);
    }
}
