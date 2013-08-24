package me.woder.world;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import me.woder.bot.Client;

@SuppressWarnings("unused")
public class Chunk {
    private boolean groundup;
    private boolean lighting;
    private int abitmap;
    private int pbitmap;
    private int x;
    private int z;
    private byte[] blocks;
    public List<Part> parts;
    public int blocknum;
    private int ablocks;
    private Client c;
    
    public Chunk(Client c, int x, int z, int pbitmap, int abitmap, boolean lighting, boolean groundup) {
        // Create chunk sections.
        this.c = c;
        this.groundup = groundup;
        this.lighting = lighting;
        this.pbitmap = pbitmap;
        this.abitmap = abitmap;
        this.x = x;
        this.z = z;
        parts = new ArrayList<Part>();

        blocknum = 0;
        ablocks = 0;
        System.out.println(Integer.toBinaryString(pbitmap));
        for (int i = 0; i < 16; i++) {
            //System.out.println("Bit map = " + (pbitmap & (1 << i)));
            if ((pbitmap & (1 << i)) != 0) {
                blocknum++; // "parts of the chunk"
                parts.add(new Part((byte)i, c));
                System.out.println("Added new part: " + i);
            }
        }

        for (int i = 0; i < 16; i++) {
            if ((pbitmap & (1 << i)) == 1) {
                ablocks++;
            }
        }

        // Number of sections * blocks per section = blocks in this "Chunk"
        blocknum = blocknum * 4096;
    }
    
    public void fillChunk(){
        int offset = 0;
        int current = 0;
        
        for (int i = 0; i < 16; i++) {
            if ((pbitmap & (1 << i)) != 0) {

                byte[] temp = new byte[4096];

                System.arraycopy(blocks, offset, temp, 0, 4096);
                Part mySection = parts.get(current);
                System.out.println("Part is: " + mySection.y);
                mySection.blocks = temp;
                offset += 4096;
                current += 1;
            }
        }
        
     }
    
     public int getBlockId(int Bx, int By, int Bz) {
        Part part = GetSectionByNumber(By);
        return part.getBlock(getXinSection(Bx), GetPositionInSection(By), getZinSection(Bz), Bx, By, Bz).getTypeId();
     }

     public Block getBlock(int Bx, int By, int Bz) {
        Part part = GetSectionByNumber(By);
        return part.getBlock(getXinSection(Bx), GetPositionInSection(By), getZinSection(Bz), Bx, By, Bz);    
     }

     public void updateBlock(int Bx, int By, int Bz, int id) {
        // Updates the block in this chunk.
        // TODO: Ensure that the block being updated is in this chunk.
        // Even though chances of that exception throwing are tiny.

        Part part = GetSectionByNumber(By);
        part.setBlock(getXinSection(Bx), GetPositionInSection(By), getZinSection(Bz), (byte) id);

     }
    
     public byte[] getData(byte[] deCompressed) {
        // Loading chunks, network handler hands off the decompressed bytes
        // This function takes its portion, and returns what's left.

        blocks = new byte[blocknum];
        byte[] temp;
        int removeable = blocknum;

        if (lighting == true)
            removeable += (blocknum / 2);

        if (groundup)
            removeable += 256;

        System.arraycopy(deCompressed, 0, blocks, 0, blocknum);
        System.out.println(deCompressed.length);
        System.out.println("Blocknum" + (blocknum + removeable));
        temp = new byte[deCompressed.length - (blocknum + removeable)];

        System.arraycopy(deCompressed, (blocknum + removeable), temp, 0, temp.length);

        fillChunk(); // Populate all of our sections with the bytes we just aquired.

        return temp;
     }
     
     public Integer getX(){
         return x;
     }
     
     public Integer getZ(){
         return z;
     }
    
     private Part GetSectionByNumber(int blockY) {
         Part thisSection = null;

         for(Part y : parts) {
             if (y.y == blockY / 16) {
                 thisSection = y;
                 break;
             }
         }

         if (thisSection == null) { // Add a new section, if it doesn't exist yet.
             thisSection = new Part((byte)(blockY / 16),c);
             parts.add(thisSection);
         }

         return thisSection;
     }

     private int getXinSection(int BlockX) {
         return BlockX - (x * 16);
     }     

     private int GetPositionInSection(int blockY) {
         return blockY & (16 - 1); // Credits: SirCmpwn Craft.net
     }
     
     private int getZinSection(int BlockZ) {
         return BlockZ - (z * 16);
     }
    
    

}
