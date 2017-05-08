
package me.woder.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoverEvent {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("value")
    @Expose
    private Value value;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
