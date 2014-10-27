package me.woder.world;

import me.woder.bot.Client;

public class Part {
    Client c;
    int[] blocks;
    //byte[] blocks;
    byte y;

    public Part(byte i, Client c) {
        this.c = c;
        y = i;
        blocks = new int[4096];
        //blocks = new byte[4096];
    }
    
    public void setBlock(int x, int y, int z, int id, int meta){
        int loc = x + (z * 16) + (y * 256);
        int c = ((id << 4) & 0xfff0) | (meta & 0xf);
        blocks[loc] = c;
        //blocks[loc] = id;
        //this.c.chat.sendMessage("At this point meta is:" + meta);
    }
    
    public Block getBlock(int x, int y, int z, int bx, int by, int bz){     
        int loc = x + (z * 16) + (y * 256);
        int block = ((blocks[loc] & 0xfff0) >> 4) & 0xffff;
        int meta = (blocks[loc] & 0xf) & 0xffff;
        //int block = blocks[loc];
        //c.chat.sendMessage("and at this point meta is:" + meta + "and block id is:" + block);
        return new Block(c.whandle.getWorld(), bx, by, bz, block, meta);        
    }

}
