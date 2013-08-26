package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;

import me.woder.world.Location;

public class Player extends Entity{
    public int entityid;
    public String playername = "";
    public int xi;
    public int yi;
    public int zi;
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public short currentitem;
    Client c;
    
    public Player(Client c){
        super(c);
        this.c = c;
        try {
            parsePacket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Player(Client c, String name, int x, int y, int z){
        super(c);
        //TODO make this work
    }
    
    public void parsePacket() throws IOException{
        entityid = c.in.readInt();
        short len = c.in.readShort();
        playername = ChatColor.stripColor(getString(c.in, len, 17));
        c.chat.sendMessage("Player " + ChatColor.stripColor(playername) + " spawned next to me");
        xi = c.in.readInt();
        yi = c.in.readInt();
        zi = c.in.readInt();
        x = xi / 32;
        y = yi / 32;
        z = zi / 32;
        c.chat.sendMessage(x + "," + y + "," + z);
        yaw = c.in.readByte();
        pitch = c.in.readByte();
        currentitem = c.in.readShort();
        c.proc.readWatchableObjects(c.in);
    }
    
    @Override
    public int getEntityId(){
        return entityid;
    }
    
    @Override
    public Entity getEntity(){
        return this;    
    }
    
    @Override
    public void updateLocation(Location l){
        this.xi = l.getBlockX();
        this.yi = l.getBlockY();
        this.zi = l.getBlockZ();
        this.x = xi/32;
        this.y = yi/32;
        this.z = zi/32;
        c.chat.sendMessage("Y is: " + y + " and " + yi);
    }
    
    @Override
    public void setLocation(Location l){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
    }
    
    @Override
    public void setALocation(Location l){
        this.xi = l.getBlockX();
        this.yi = l.getBlockY();
        this.zi = l.getBlockZ();
        this.x = xi/32;
        this.y = yi/32;
        this.z = zi/32;
        c.chat.sendMessage("Z is: " + z + " and " + zi);
    }
    
    public static String getString(DataInputStream datainputstream, int length,
            int max) throws IOException {
        if (length > max)
            throw new IOException(
                    "Received string length longer than maximum allowed ("
                            + length + " > " + max + ")");
        if (length < 0) {
            throw new IOException(
                    "Received string length is less than zero! Weird string!");
        }
        StringBuilder stringbuilder = new StringBuilder();

        for (int j = 0; j < length; j++) {
            stringbuilder.append(datainputstream.readChar());
        }

        return stringbuilder.toString();
    }
    
    public String getName(){
        return playername;
    }
    
    @Override
    public Location getLocation(){
        return new Location(c.whandle.getWorld(), x, y, z);
    }
    
    @Override
    public Location getALocation(){
        return new Location(c.whandle.getWorld(), xi, yi, zi);
    }
    
    public Location getLocationUnder(){
        return new Location(c.whandle.getWorld(), x, y-1, z);
    }

}
