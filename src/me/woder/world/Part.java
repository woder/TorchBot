package me.woder.world;

import me.woder.bot.Client;

public class Part {
    Client c;
    long[] blocks;
    int[] palette;
    int entryMask;
    int bitsPerEntry;
    //byte[] blocks;
    byte y;

    public Part(byte i, Client c) {
        this.c = c;
        y = i;
        blocks = new long[256];
        //blocks = new byte[4096];
    }
    
   /* public void setBlock(int x, int y, int z, int id, int meta){
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
    }*/
    
    public void setBlock(int x, int y, int z, int id, int meta){
        int loc = x + (z * 16) + (y * 256);
        int index = 0;
        for(int i = 0; i < palette.length; i++){
            int block =(palette[i] >> 4) & 0x0F;
            if(block == id){
                index = i;
            }
        }
        //blocks[loc] = id;
        //this.c.chat.sendMessage("At this point meta is:" + meta);
    }
    
    public Block getBlock(int x, int y, int z, int bx, int by, int bz){     
        int loc = x + (z * 16) + (y * 256);
        System.out.println("The y of this is: " + this.y + " and size: " + blocks.length);
        int block =(palette[get(loc)] >> 4) & 0x0F;
        int meta = (palette[get(loc)] & 0xf);
        //int block = blocks[loc];
        //c.chat.sendMessage("and at this point meta is:" + meta + "and block id is:" + block);
        return new Block(c.whandle.getWorld(), bx, by, bz, block, meta);        
    }
    
    public int get(int pos){
        int startBit = pos * this.bitsPerEntry;
        int startLong = startBit / 64;
        int endLong = ((pos + 1) * this.bitsPerEntry - 1) / 64;
        int startOffset = startBit % 64;

        if (startLong == endLong)
        {
            return (int)(this.blocks[startLong] >>> startOffset & this.entryMask);
        }
        else
        {
            int endOffset = 64 - startOffset;
            return (int)((this.blocks[startLong] >>> startOffset | this.blocks[endLong] << endOffset) & this.entryMask);
        }
    }
    
    public void set(int index, int value) {
        if(index < 0 || index > this.blocks.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        int bitIndex = index * this.bitsPerEntry;
        int startIndex = bitIndex / 64;
        int endIndex = ((index + 1) * this.bitsPerEntry - 1) / 64;
        int startBitSubIndex = bitIndex % 64;
        this.blocks[startIndex] = this.blocks[startIndex] & ~(this.entryMask << startBitSubIndex) | ((long) value & this.entryMask) << startBitSubIndex;
        if(startIndex != endIndex) {
            int endBitSubIndex = 64 - startBitSubIndex;
            this.blocks[endIndex] = this.blocks[endIndex] >>> endBitSubIndex << endBitSubIndex | ((long) value & this.entryMask) >> endBitSubIndex;
        }
    }
    

}
