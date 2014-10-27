package me.woder.bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.event.Event;
import me.woder.network.Packet;
import net.sf.json.JSON;
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
        c.net.sendPacket(buf, c.out);
       } catch(IOException e){
           e.printStackTrace();
       }
    }
    
    public String formatMessage(String message){
        String mess = "Something went wrong";
        try{
         JSON jsonr = JSONSerializer.toJSON(message);     
         if(!jsonr.isArray()){
          JSONObject json = (JSONObject) jsonr;
          if(json.containsKey("translate")){
           String key = json.getString("translate");
           c.gui.addText(message);
           if(key.equalsIgnoreCase("chat.type.text")){              
               JSONArray arr = json.getJSONArray("with");
               formatWith(json, arr);
           }   
          }else if(json.containsKey("extra")){
               formatExtra(json);
          }
         }else{
            //TODO add method to parse this
         }
        }catch(JSONException ex){
           c.gui.addText("§4Invalid json received; string skipped");
           err.log(Level.WARNING, "MESSAGE: " + message + " IS NOT VALID JSON, SKIPPING STRING...");
           ex.printStackTrace();
        }
        return mess;
    }
    
    public void formatWith(JSONObject json, JSONArray arr){
        JSONObject hoverevent = getHover(arr);
        JSONObject value = hoverevent.getJSONObject("value");
        String user = value.getString("name");
        //String uuid = value.getString("id"); Maybe we can find a use for this later
        String mess = arr.getString(1);
        String formated = mess;
        if(json.containsKey("color")){
         String colour = json.getString("color");
         formated = "§" + attributes.get(colour) + mess;
        }
        c.gui.addText(formated);
        c.ehandle.handleEvent(new Event("onChatMessage", new Object[] {user, formated}));
        getCommandText(mess, user);
    }
    
    private void formatExtra(JSONObject json){
        String formated = "";
        JSONArray arr = json.getJSONArray("extra");
        formated = formatColours(arr);
        String username = getUsername(formated);
        c.gui.addText(formated);
        c.ehandle.handleEvent(new Event("onChatMessage", new Object[] {username, formated}));
        getCommandText(formated, username);
    }
    
    public JSONObject getHover(JSONArray arr){
        JSONObject ob = null;
        for(int i = 0; i < arr.size(); i++){
           JSONObject obj = arr.getJSONObject(i);
           if(obj.containsKey("hoverEvent")){
               ob = obj.getJSONObject("hoverEvent");
               break;
           }
        }
        return ob;
    }
    //Code to attempt to get the username
    public String getUsername(String formated){
        int delimiter = formated.indexOf(">"); //TODO replace with variable to make this interchangable
        int space = formated.indexOf(" ");
        if(delimiter != -1 && space != -1 && delimiter-space < 0){
           space = 0;
        }
        String username;
        if(delimiter != -1 && space != -1 && delimiter-space > -1){
         username = formated.substring(space, delimiter);
         username = ChatColor.stripColor(username.replaceAll("[:<>]", ""));
        }else{
         username = "Unknown";
        }      
        return username;
    }
    
    //Code to get the command from the string then pass it onto the command handler
    public void getCommandText(String formated, String username){
        if(formated.contains(c.prefix)){
            String commande = formated.substring(formated.indexOf(c.prefix));
            int d = commande.length();
            if(commande.indexOf(" ") != -1){
              d = commande.indexOf(" ");
            }
            String command = commande.substring(0, d);
            commande.trim();
            c.chandle.processCommand(command.replace(c.prefix, ""), commande.substring(d).split(" "), username);
        }   
    }
    
    //Code for getting all the colours together for multicoloured strings
    public String formatColours(JSONArray arr){
        String formated = "";
        for (int i = 0; i < arr.size(); i++){
            if(arr.get(i).toString().contains("{")){
             JSONObject ob = arr.getJSONObject(i);
             String key = ob.getString("color");
             String theText = ob.getString("text");
             formated = formated + "§" + attributes.get(key) + theText;
            }else{
             formated = formated + "§0" + arr.getString(i);  
            }
        }
        return formated;
    }

}
