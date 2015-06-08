package me.woder.bot;

public class BlockPartyPlayer {
    boolean enabled = true;
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
             c.gui.addText("Yeah..." + s.getId() + " " + s.getDamage());
           }
        }
    }

}
