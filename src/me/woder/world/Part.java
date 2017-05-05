package me.woder.world;

import java.util.List;

import me.woder.bot.Client;

public class Part {
    Client c;
    long[] blocks;
    List<Integer> palette;
    int entryMask;
    int bitsPerEntry;
    int[] blockse;
    //byte[] blocks;
    byte y;

    //TODO actually try to get palette working properly
    public Part(byte i, Client c) {
        this.c = c;
        y = i;
        blocks = new long[256];
        blockse = new int[4096];
    }
    
    public void setBlock(int x, int y, int z, int id, int meta){
        int loc = x + (z * 16) + (y * 256);        
        int ce = ((id << 4) & 0xfff0) | (meta & 0xf);
        blockse[loc] = ce;
        //blocks[loc] = id;
        //this.c.chat.sendMessage("At this point meta is:" + meta);
    }
    
    public Block getBlock(int x, int y, int z, int bx, int by, int bz){     
        int loc = x + (z * 16) + (y * 256);
        int block = ((blockse[loc] & 0xfff0) >> 4) & 0xffff;
        int meta = (blockse[loc] & 0xf) & 0xffff;
        
        return new Block(c.whandle.getWorld(), bx, by, bz, block, meta);        
    }
    
    public void setPalette(List<Integer> palette){
        this.palette = palette;
    }
    
    /*public Block getBlock(int x, int y, int z, int bx, int by, int bz){     
        int loc = x + (z * 16) + (y * 256);
        //System.out.println("The y of this is: " + this.y + " and size: " + blocks.length + " and? " + get(loc));
        int block =(palette.get(get(loc)) >> 4) & 0x0F;
        int meta = (palette.get(get(loc)) & 0xf);
        //int block = blocks[loc];
        //c.chat.sendMessage("and at this point meta is:" + meta + "and block id is:" + block);
        return new Block(c.whandle.getWorld(), bx, by, bz, block, meta);        
    }*/
    
    //unpacks the chunk from the bs format that minecraft saves it in now and places it in our ultra efficient woder format
    //format is simply the id of the block as a byte and the meta data as a byte
    public void unpack(){
        //loop over all blocks in this chunk
        for(int i = 0; i < blockse.length; i++){
            //first extract the index from the block array and use that to index the palette
            int indice = get(i);
            int block =(palette.get(indice) & 0xfff0) >> 4;
            int meta = (palette.get(indice) & 0xf);
            //System.out.println("Indice is: " + indice + " maps to: " + block + " " + meta);
            int c = ((block << 4) & 0xfff0) | (meta & 0xf); //inject these extracted values into our new format
            blockse[i] = c; //set our block in the array to this value          
        }
        
        blocks = null; //destroy the initial dataset        
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
    

}
