package me.woder.world;

import me.woder.bot.Client;

public class Part {
    Client c;
    byte[] blocks;
    byte y;

    public Part(byte i, Client c) {
        this.c = c;
        y = i;
        blocks = new byte[4096];
    }
    
    public void setBlock(int x, int y, int z, byte id){
        int loc = x + (z * 16) + (y * 256);
        blocks[loc] = id;
    }
    
    public Block getBlock(int x, int y, int z, int bx, int by, int bz){
        int loc = x + (z * 16) + (y * 256);
        return new Block(c.whandle.getWorld(), bx, by, bz, (int)blocks[loc]);
    }

}
