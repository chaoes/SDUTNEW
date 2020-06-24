package me.chaoe.sdutnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.chaoe.sdutnew.view.NewListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.splash);
        imageView.setVisibility(View.VISIBLE);
        final Intent intent = new Intent(this,NewListActivity.class);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}