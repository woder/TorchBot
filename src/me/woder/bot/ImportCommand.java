package me.woder.bot;

import me.woder.event.Event;
import me.woder.world.Location;

public class ImportCommand {
    public Location loc1;
    public Location loc2;
    public int imp = 0;
    public String name;
    
    public void processCommand(Client c, String[] args){
      imp = 1;
      this.name = args[1];
      c.chat.sendMessage("Place two dirt blocks to select the voxel");
    }
    
    public void onBlockChange(Event e, Client c){
        int bid = (Integer) e.param[3];
        if(bid == 3){
          if(imp == 1){
            loc1 = new Location(c.whandle.getWorld(), (Integer)e.param[0], (Integer)e.param[1], (Integer)e.param[2]); 
            c.chat.sendMessage("Location one set to: " + loc1.getX() + "," + loc1.getY() + "," + loc1.getZ() + "!");
            imp = 2;
          }else if(imp == 2){
            loc2 = new Location(c.whandle.getWorld(), (Integer)e.param[0], (Integer)e.param[1], (Integer)e.param[2]);    
            c.chat.sendMessage("Location two set to: " + loc2.getX() + "," + loc2.getY() + "," + loc2.getZ() + "!");
            c.whandle.getWorld().importer.exportb(c, loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), name);
            imp = 0;
          }
        }
    }
    
    

}
