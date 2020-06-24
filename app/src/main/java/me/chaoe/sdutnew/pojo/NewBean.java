package me.chaoe.sdutnew.pojo;

public class NewBean {
    private String title;
    private String text;
    private String date;
    private String url;

    @Override
    public String toString() {
        return "NewBean{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public NewBean() {
    }

    public NewBean(String title, String text, String date, String url) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
