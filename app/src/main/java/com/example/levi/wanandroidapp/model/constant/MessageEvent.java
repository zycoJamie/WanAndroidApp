package com.example.levi.wanandroidapp.model.constant;

/**
 * Created by zyco on 2018/9/5
 */
public class MessageEvent {
    public int code;
    public String msg;
    private String name;

    public MessageEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MessageEvent(int code, String msg, String name) {
        this.code = code;
        this.msg = msg;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
