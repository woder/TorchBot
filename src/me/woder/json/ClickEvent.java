
package me.woder.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClickEvent {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("value")
    @Expose
    private String value;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
