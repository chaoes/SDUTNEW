package me.chaoe.sdutnew.service;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.chaoe.sdutnew.MyApplication;
import me.chaoe.sdutnew.R;
import me.chaoe.sdutnew.pojo.NewBean;

public class NewServiceI implements NewService {
//    private static String url = "https://lgwindow.sdut.edu.cn";
    private static String url = MyApplication.getContext().getString(R.string.url);
    private List list;
    public NewServiceI() {
        list = new ArrayList();
        list.add(url+"/1058/list");
        list.add(url+"/zhxw/list");
        list.add(url+"/1073/list");
        list.add(url+"/jxky/list");
    }

    @Override
    public List<NewBean> getnewslist(int kind, int page) throws IOException {
        String useurl = (String)list.get(kind);
        useurl+=page;
        useurl+=".htm";
        Document document = Jsoup.connect(useurl).header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Safari/537.36").get();
        List<NewBean> newlist = new ArrayList<NewBean>();
        Element postList = document.select("tbody").get(2);
        Elements postItems = postList.select("tr");
        for (Element postItem : postItems) {
            Elements titleEle = postItem.select(".list_tit [target='_blank']");
            Elements contentEle = postItem.select(".list_content [target='_blank']");
            Elements timeEle = postItem.select(".list_time [class='lt_b']");
            NewBean data = new NewBean();
            data.setText(contentEle.text());
            data.setTitle(titleEle.text());
            data.setDate(timeEle.text());
            data.setUrl(url+titleEle.attr("href"));
            Log.d("getdate",data.toString());

            newlist.add(data);
        }
        return newlist;
    }

    @Override
    public String getnew(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element postList1 = document.getElementsByClass("con_left clearfix").get(0);
        Element postList = document.getElementsByClass("wp_articlecontent").get(0);
        Elements imgItems = postList.select("[data-layer='photo']");
        String imgUrl;
        for (Element imgItem: imgItems) {
            imgUrl = this.url + imgItem.attr("src");
            imgItem.attr("src", imgUrl);
            imgItem.attr("original-src", imgUrl);
            imgItem.attr("max-width", "100%")
                    .attr("height", "auto");
            imgItem.attr("style", "max-width:100%;height:auto");
        }
        return postList1.toString();
    }
}
