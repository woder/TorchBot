package me.woder.bot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.event.Event;
import me.woder.network.Packet;
import net.sf.json.JSONArray;
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
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 1);
        Packet.writeString(buf, message);
        Packet.sendPacket(buf, c.out);
       } catch(IOException e){
           e.printStackTrace();
       }
    }
    
    /*public String readMessage(){
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
    }*/
    
    public String formatMessage(String message){
        String mess = "Something went wrong";
        String user = "Unknown";
        System.out.println(message);
        JSONObject json = (JSONObject) JSONSerializer.toJSON(message);     
        //JSONArray text = rec.;
       try{
        JSONArray arr = json.getJSONArray("with");
        if(arr.toArray().length > 1){
         mess = arr.getString(1);
         JSONObject te = arr.getJSONObject(0);
         user = te.getString("text");
        }
        log.log(Level.FINEST,mess);
        c.ehandle.handleEvent(new Event("onChatMessage", new Object[] {user, mess}));
        c.gui.addText("§0" + user + ": " + mess);       
        if(mess.contains("!")){
            c.chandle.processCommand(mess);
        }
       }catch(JSONException e){
         c.gui.addText("§4Formating error!");
       }            
        return mess;
    }

}
