package me.chaoe.sdutnew.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;

import me.chaoe.sdutnew.R;
import me.chaoe.sdutnew.service.NewService;
import me.chaoe.sdutnew.service.NewServiceI;

public class ANewActivity extends AppCompatActivity {
    static NewService newService = new NewServiceI();
    String title;
    String date;
    String text;
    String url;
    WebView art;
    Toolbar toolbar;
    NewHandler handler=new NewHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_new);
        art = (WebView)findViewById(R.id.arttext);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle(this.title);
        toolbar.setSubtitle(this.date);
        Intent intent = getIntent();
        this.title=intent.getStringExtra("title");
        this.date=intent.getStringExtra("date");
        this.text=intent.getStringExtra("text");
        this.url=intent.getStringExtra("url");
        Log.d("url",url+"=====================================");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            art.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        art.getSettings().setBlockNetworkImage(false);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String art = newService.getnew(url);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = art;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    private class NewHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1) {
                Log.d("html",(String)msg.obj);
                art.loadData((String)msg.obj,"text/html; charset=UTF-8;",null);

            }
        }
    }
}