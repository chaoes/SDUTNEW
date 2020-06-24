package me.chaoe.sdutnew.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.chaoe.sdutnew.MainActivity;
import me.chaoe.sdutnew.R;
import me.chaoe.sdutnew.adapter.MyAdapter;
import me.chaoe.sdutnew.pojo.NewBean;
import me.chaoe.sdutnew.service.NewService;
import me.chaoe.sdutnew.service.NewServiceI;

public class NewListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RefreshLayout refreshLayout;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TabLayout tabLayout;
    List<NewBean> mynewslist;
    static NewService newService = new NewServiceI();
    MyHandler handler;
    int kind;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        mynewslist= new ArrayList<NewBean>();
        handler = new MyHandler();
        page = 1;
        kind = 0;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tab_lay);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.new1).setTag(1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.new2).setTag(2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.new3).setTag(3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.new4).setTag(4));
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        recyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(mynewslist);
        myAdapter.setOnClickListener(new MyAdapter.OnClickListener() {
            @Override
            public void onItemClick(View view, int posiotion,NewBean nnew) {
//                Toast.makeText(NewListActivity.this,"点击了第"+posiotion+"条",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewListActivity.this,ANewActivity.class);
                intent.putExtra("title",nnew.getText());
                intent.putExtra("date",nnew.getDate());
                intent.putExtra("text",nnew.getText());
                intent.putExtra("url",nnew.getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newsreflash();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page+=1;
                getnewslist(kind,page);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tag = (int)tab.getTag();
                Log.d("cc","tatatat: "+tab.getTag());
                switch (tag){
                    case 1:
                        kind = 0;
                        refreshLayout.autoRefresh();
                        break;
                    case 2:
                        kind =1;
                        refreshLayout.autoRefresh();
                        break;
                    case 3:
                        kind =2;
                        refreshLayout.autoRefresh();
                        break;
                    case 4:
                        kind = 3;
                        refreshLayout.autoRefresh();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                this.onTabSelected(tab);

            }
        });
        refreshLayout.autoRefresh();
    }
    public void getnewslist(final int kind, final int page){
        Log.d("cc","load---------------");
        final List<NewBean>[] newslist = new List[]{null};
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                    try {
                        synchronized (newslist){newslist[0] =newService.getnewslist(kind,page);}
                        Message message = Message.obtain();
                        message.obj = newslist[0];
                        message.what=1;
                        Log.d("cc","msg="+message.what);
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        };
        thread.start();
    }
    public void newsreflash(){
        Log.d("cc","reflash---------------");
        page = 1;
        myAdapter.removeall();
        getnewslist(kind,page);
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.d("cc","msg="+msg.what);
            switch (msg.what){
                case 1:
                    Log.d("cc","loading---------------");
                    myAdapter.addlist((ArrayList<NewBean>)msg.obj);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    break;
                default:
                    break;
            }
        }
    };
}