package coe1530.eatyourleftovers;

public class Event {
    public int type;
    public String msg;
    public Event(int type,String msg) { this.type = type; this.msg = msg; }
    public String getMsg(){ return this.msg;}
}