package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ChatHandler {
    Client c;
    
    public ChatHandler(Client c){
        this.c = c;
    }
    
    public void sendMessage(String message){
       try{
        c.out.writeByte(0x03);
        c.out.writeShort(message.length());
        c.out.writeChars(message);
        c.out.flush();
       } catch(IOException e){
           e.printStackTrace();
       }
    }
    
    public String readMessage(){
        short len;
        String messages = null;
        try {
            len = c.in.readShort();
            messages = getString(c.in, len, 1500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(messages.contains("!")){
            c.chandle.processCommand(formatMessage(messages));
            System.out.println(formatMessage(messages));
        }
        System.out.println(messages);
        return messages;
    }
    
    public String formatMessage(String message){
        String mess = "";
        JSONObject json = (JSONObject) JSONSerializer.toJSON(message);     
        //JSONArray text = rec.;
        mess = json.getString("text");
        return mess.replace("§", "&");
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
