package me.chaoe.sdutnew;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import me.chaoe.sdutnew.pojo.NewBean;
import me.chaoe.sdutnew.service.NewServiceI;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void getnewlist() {
        NewServiceI newServiceI = new NewServiceI();
        List<NewBean> list = null;
        try {
            list = newServiceI.getnewslist(0,1);
            for(NewBean newBean:list){
                System.out.println(newBean.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getnewpage(){
        NewServiceI newServiceI = new NewServiceI();
        try {
            System.out.println(newServiceI.getnew("https://lgwindow.sdut.edu.cn/2020/0609/c1058a384792/page.htm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}