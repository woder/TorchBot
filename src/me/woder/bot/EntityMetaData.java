package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityMetaData {
    public int entityid;
    DataInputStream in;
    List<Slot> metabyte = new ArrayList<Slot>();
    List<Slot> metashort = new ArrayList<Slot>();
    List<Slot> metastring = new ArrayList<Slot>();
    List<Slot> inv = new ArrayList<Slot>();
    
    public EntityMetaData(DataInputStream in, int entityid){
        this.entityid = entityid;
        this.in = in;
        try {
            parseData(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
    public void parseData(DataInputStream in) throws IOException{
      
    }
    
    public static String getString(DataInputStream datainputstream, int length,
            int max) throws IOException {
        if (length > max)
            throw new IOException(
                    "Received string length longer than maximum allowed ("
                            + length + " > " + max + ")");
        if (length < 0) {
            throw new IOException(
                    "Received string length is less than zero! Weird string!");
        }
        StringBuilder stringbuilder = new StringBuilder();

        for (int j = 0; j < length; j++) {
            stringbuilder.append(datainputstream.readChar());
        }

        return stringbuilder.toString();
    }

}
