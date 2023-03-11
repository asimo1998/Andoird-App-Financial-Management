package com.example.spendingmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBindings;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spendingmanagement.model.PhanLoaiActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.orange)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PhanLoaiActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}