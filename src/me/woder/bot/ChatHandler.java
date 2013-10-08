package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ChatHandler {
    Client c;
    private Logger log;
    
    public ChatHandler(Client c){
        this.c = c;
        log = Logger.getLogger("me.woder.chat");
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
    
    public String readString(){
        short len;
        String messages = null;
        try {
            len = c.in.readShort();
            messages = getString(c.in, len, 1500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //log.log(Level.FINEST,messages.trim());
        return messages;
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
            log.log(Level.FINEST,formatMessage(messages));
        }
        log.log(Level.FINEST,messages);
        c.gui.addText(formatMessage(messages));
        return messages;
    }
    
    public String formatMessage(String message){
        String mess = "Something went wrong";
        JSONObject json = (JSONObject) JSONSerializer.toJSON(message);     
        //JSONArray text = rec.;
       try{
        mess = json.getString("text");
       }catch(JSONException e){
         c.gui.addText("§4Formating error!");
       }
        return mess;
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
