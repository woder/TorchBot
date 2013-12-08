package me.woder.bot;

import java.io.IOException;
import java.util.HashMap;
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
    private Logger err;
    //support for legacy system:
    HashMap<String, String> attributes;
    
    
    public ChatHandler(Client c){
        this.c = c;
        log = Logger.getLogger("me.woder.chat");
        err = Logger.getLogger("me.woder.error");
        attributes = new HashMap<String, String>();
        attributes.put("black", "0");
        attributes.put("dark_blue", "1");
        attributes.put("dark_green", "2");
        attributes.put("dark_aqua", "3");
        attributes.put("dark_red", "4");
        attributes.put("dark_purple", "5");
        attributes.put("gold", "6");
        attributes.put("gray", "7");
        attributes.put("dark_gray", "8");
        attributes.put("blue", "9");
        attributes.put("green", "a");
        attributes.put("aqua", "b");
        attributes.put("red", "c");
        attributes.put("light_purple", "d");
        attributes.put("yellow", "e");        
        attributes.put("white", "f");
        attributes.put("reset", "r");
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
       if(json.containsKey("with")){
         mess = formatWith(json, mess, user);
         
       }else if(json.containsKey("extra")){
         mess = formatExtra(json);
       }else{
           mess = formatRaw(mess, json); 
       }      
        return mess;
    }
    
    public String formatWith(JSONObject json, String mess, String user){
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
            String[] args = mess.split(" ");
            if(mess.contains(c.prefix)){
                c.chandle.processCommand(args[0].replace(c.prefix, ""), args, user);
            }
         }catch(JSONException e){
             e.printStackTrace();
             err.log(Level.SEVERE, "FORMATERROR: " + e.getMessage()  + " (" + e.getCause() + ")");
             c.gui.addText("§4Formating error!");
         }         
        return mess;
    }
    
    private String formatExtra(JSONObject json){
        String formated = "";
        JSONArray arr = json.getJSONArray("extra");
        for (int i = 0; i < arr.size(); i++){
           if(arr.get(i).toString().contains("{")){
            JSONObject ob = arr.getJSONObject(i);
            String key = ob.getString("color");
            String theText = ob.getString("text");
            System.out.println("color: " + key + " text: " + theText);
            formated = formated + "§" + attributes.get(key) + theText;
           }else{
            formated = formated + "§0" + arr.getString(i);  
           }
        }
        c.gui.addText(formated);     
        String[] args = formated.split(" ");
        if(formated.contains(c.prefix)){
            c.chandle.processCommand(args[1].replace(c.prefix, ""), args, "Unknown");
        }
        return formated;
    }
    
    public String formatRaw(String mess, JSONObject json){
        try{
            mess = json.getString("text");
            log.log(Level.FINEST,mess);
            c.ehandle.handleEvent(new Event("onChatMessage", new Object[] {"Unknown", mess}));
            String username = mess.substring(0, mess.indexOf(" "));
            username = username.replace("[<>:\\[\\]]", "");
            String[] args = mess.split(" ");
            c.gui.addText("§0" + mess);       
            if(mess.contains(c.prefix)){
                c.chandle.processCommand(args[0].replace(c.prefix, ""), args, username);
            }
         }catch(JSONException e){
             e.printStackTrace();
             err.log(Level.SEVERE, "FORMATERROR: " + e.getMessage()  + " (" + e.getCause() + ")");
             c.gui.addText("§4Formating error!");
         }         
        return mess;
    }

}
