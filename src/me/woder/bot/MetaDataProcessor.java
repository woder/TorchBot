package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.network.ByteArrayDataInputWrapper;
import me.woder.network.Packet;

public class MetaDataProcessor {
    Client c;
    
    public MetaDataProcessor(Client c){
      this.c = c;    
    }
    
    @SuppressWarnings("rawtypes")
    public List readWatchableObjects(ByteArrayDataInputWrapper in) throws IOException{
        ArrayList var1 = null;
        System.out.println("Now reading metadata...");
        while(true){
            int var2 = (in.readByte() & 0xff);
            System.out.println(var2);
            if(var2 == 127)break;
            

            int index = var2 & 0x1f;
            int type = var2 >> 5;
            //WatchableObject var5 = null;

            switch (type){
                case 0:
                     byte me = in.readByte();
                     System.out.println("Reading byte: " + me + " at index: " + index);
                    break;

                case 1:
                    short mo = in.readShort();
                    System.out.println("Reading short: " + mo + " at index: " + index);
                    break;

                case 2:       
                    int mz = in.readInt();
                    System.out.println("Reading int: " + mz + " at index: " + index);
                    break;

                case 3:         
                    float ma = in.readFloat();
                    System.out.println("Reading float: " + ma + " at index: " + index);
                    break;

                case 4:
                    System.out.println("well then " + Packet.getString(in));
                    break;

                case 5:
                    System.out.println("Reading slot");
                    new SlotHandler().processSlots(in, 0);
                    break;

                case 6:
                    int var6 = in.readInt();
                    int var7 = in.readInt();
                    int var8 = in.readInt();
                    System.out.println("Coords are: " + var6 + "," + var7 + "," + var8);
            }

        }

        return var1;
    }

}
