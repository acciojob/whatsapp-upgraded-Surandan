package com.driver;

public class User {
    private String name;
    private String mobile;
    private int msgSent;

    public User() {
    }

    public User(String name, String mobile, int msgSent) {
        this.name = name;
        this.mobile = mobile;
        this.msgSent = msgSent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMsgSent() {
        return msgSent;
    }

    public void setMsgSent(int msgSent) {
        this.msgSent = msgSent;
    }
}
