package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class PostBlock implements Serializable {

    private boolean Default;
    private String Content;

    public PostBlock(boolean Default, String Content) {
        this.Default = Default;
        this.Content = Content;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean Default) {
        this.Default = Default;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
