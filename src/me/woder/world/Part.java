package me.woder.world;

import me.woder.bot.Client;

public class Part {
    Client c;
    short[] blocks;
    //byte[] blocks;
    byte y;

    public Part(byte i, Client c) {
        this.c = c;
        y = i;
        blocks = new short[4096];
        //blocks = new byte[4096];
    }
    
    public void setBlock(int x, int y, int z, byte id){
        int loc = x + (z * 16) + (y * 256);
        blocks[loc] = id;
    }
    
    public void setBlock(int x, int y, int z, byte id, byte meta){
        int loc = x + (z * 16) + (y * 256);
        short c = (short) (id & 0xff);
        c = (short) (c + ((meta & 0xf) << 8));
        blocks[loc] = c;
        //blocks[loc] = id;
        //this.c.chat.sendMessage("At this point meta is:" + meta);
    }
    
    public Block getBlock(int x, int y, int z, int bx, int by, int bz){     
        int loc = x + (z * 16) + (y * 256);
        System.out.println("x,y,z" + x + "," + y + "," + z);
        byte meta = (byte) ((blocks[loc] >> 8) & 0xf);
        int block = blocks[loc] & 0xff;
        //int block = blocks[loc];
        //c.chat.sendMessage("and at this point meta is:" + meta + "and block id is:" + block);
        return new Block(c.whandle.getWorld(), bx, by, bz, block, meta);        
    }

}
