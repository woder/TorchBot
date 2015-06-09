package me.woder.bot;

import me.woder.world.Block;

public class BlockPartyPlayer {
    boolean enabled = false;
    boolean alreadyfound = false;
    Client c;
    int lastblock = 0;
    int meta = 0;
    
    public BlockPartyPlayer(Client c){
        this.c = c;
    }
    
    public void gameLoop(){
        if(this.enabled){
           Slot s = c.invhandle.getSlot(40);
           if(s != null){
              System.out.println(s.getId() + " and " + lastblock + " meta: " + s.getDamage() + " " + meta);
              if(s.getId() != lastblock || s.getDamage() != meta){
                 lastblock = s.getId();
                 meta = s.getDamage();
                 for(int x = -20; x <= 20; x++){
                     for(int z = -20; z <= 20; z++){
                         if(!alreadyfound){
                          int xp = c.location.getBlockX() + x;
                          int yp = c.location.getBlockY()-1;
                          int zp = c.location.getBlockZ() + z;
                          Block b = c.whandle.getWorld().getBlock(xp, yp, zp);
                          if(b != null){
                           if(b.getTypeId() == lastblock && b.getMetaData() == meta){
                              c.chat.sendMessage("Moving to: " + xp + " " + (yp+1) + " " + zp);
                              c.move.goToLocation(xp, yp+1, zp);  
                              alreadyfound = true;
                           }
                         }
                        }
                     }
                 }
                 alreadyfound = false;
              }
           }
        }
    }

}
