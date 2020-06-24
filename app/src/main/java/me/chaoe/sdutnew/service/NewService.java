package me.chaoe.sdutnew.service;

import java.io.IOException;
import java.util.List;

import me.chaoe.sdutnew.pojo.NewBean;

public interface NewService {
    List<NewBean> getnewslist(int kind,int page) throws IOException;
    String getnew(String url) throws IOException;
}
