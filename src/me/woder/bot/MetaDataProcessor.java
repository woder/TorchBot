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
        while(true){
            int var2 = (in.readByte() & 0xff);
            if(var2 == 127)break;
            

            //int index = var2 & 0x1f;
            int type = var2 >> 5;
            //WatchableObject var5 = null;

            switch (type){
                case 0:
                     in.readByte();
                    break;

                case 1:
                    in.readShort();
                    break;

                case 2:       
                    in.readInt();
                    break;

                case 3:         
                    in.readFloat();
                    break;

                case 4:
                    Packet.getString(in);
                    break;

                case 5:
                    new SlotHandler().processSlots(in, 0);
                    break;

                case 6:
                    in.readInt();
                    in.readInt();
                    in.readInt();
            }

        }

        return var1;
    }

}
