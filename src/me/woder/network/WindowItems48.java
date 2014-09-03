package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Slot;
import me.woder.bot.SlotHandler;

import com.google.common.io.ByteArrayDataInput;

public class WindowItems48 extends Packet{
    public WindowItems48(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        buf.readByte();
        short count = buf.readShort();
        for(int i = 0; i < count;i++){
            Slot s = new SlotHandler().processSlots(buf, i);
            c.invhandle.setSlot(s);
        }       
    }

}
