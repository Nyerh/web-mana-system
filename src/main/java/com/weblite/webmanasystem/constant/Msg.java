package com.weblite.webmanasystem.constant;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 9:59
 * @Description:
 */
public class Msg {

    private int state;
    private int total;
    private String msg;
    private Object items;

    public Msg()
    {
    }

    public Msg(int state, Object items) {
        this.state = state;
        this.items = items;
    }

    public Msg(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Msg(int state, int total, String msg, Object items)
    {
        this.state = state;
        this.total=total;
        this.msg = msg;
        this.items = items;
    }

    public int getState() {
        return state;
    }

    public Msg setState(int state) {
        this.state = state;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public Msg setTotal(int total) {
        this.total = total;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Msg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getItems() {
        return items;
    }

    public Msg setItems(Object items) {
        this.items = items;
        return this;
    }
}
