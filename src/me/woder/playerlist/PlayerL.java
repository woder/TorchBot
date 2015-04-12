package me.woder.playerlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Class for storing player list items
public class PlayerL{
    UUID id;
    String name;
    List<Property> properties = new ArrayList<Property>();
    int gamemode;
    int ping;
    boolean hasdisplayname;
    String displayname;
    
    public PlayerL(UUID id, String name, List<Property> properties, int gamemode, int ping, boolean hasdisplayname, String displayname){
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.gamemode = gamemode;
        this.ping = ping;
        this.hasdisplayname = hasdisplayname;
        this.displayname = displayname;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public boolean isHasdisplayname() {
        return hasdisplayname;
    }

    public void setHasdisplayname(boolean hasdisplayname) {
        this.hasdisplayname = hasdisplayname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setEverything(UUID id, String name, List<Property> properties, int gamemode, int ping, boolean hasdisplayname, String displayname){
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.gamemode = gamemode;
        this.ping = ping;
        this.hasdisplayname = hasdisplayname;
        this.displayname = displayname;
    }
}
