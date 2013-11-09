package me.woder.event;

public class Event {
    public String type;
    public Object[] param;
    
    public Event(String type2, Object[] param2){
        this.type = type2;
        this.param = param2;
    }
    
}
