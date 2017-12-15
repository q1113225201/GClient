package com.sjl.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private Toolbar toolBar;
    private TextView tvDate;
    private ImageView ivGirl;
    private void initView() {
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        tvDate = findViewById(R.id.tvDate);
        ivGirl = findViewById(R.id.ivGirl);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolBar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.back){
                    finish();
                }else if(item.getItemId()==R.id.test){
                    Toast.makeText(MainActivity.this,"测试",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
}
