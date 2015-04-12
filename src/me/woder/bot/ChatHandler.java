package me.woder.bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.woder.event.Event;
import me.woder.json.ChatMessage;
import me.woder.json.ChatMessageDe;
import me.woder.json.ChatMessageDezerializer;
import me.woder.json.With;
import me.woder.json.Node;
import me.woder.network.Packet;
import me.woder.playerlist.PlayerL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChatHandler {
    Client c;
    private Logger log;
    private Logger err;
    // support for legacy system:
    HashMap<String, String> attributes;

    public ChatHandler(Client c) {
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

    public void sendMessage(String message) {
        try {
            ByteArrayDataOutput buf = ByteStreams.newDataOutput();
            Packet.writeVarInt(buf, 1);
            Packet.writeString(buf, message);
            c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(List<String> s, int delayed) {
        try {
            ByteArrayDataOutput buf = ByteStreams.newDataOutput();
            Packet.writeVarInt(buf, 1);
            Packet.writeString(buf, s.get(delayed));
            c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String formatMessage(String message) {
        String mess = "Something went wrong";
        String username = "Unknown";
        String formated = "";

        Gson gson = null;
        if (message.contains("\"with\":[")) {
            gson = new GsonBuilder().registerTypeAdapter(ChatMessage.class,
                    new ChatMessageDezerializer()).create();
        } else if (message.contains("\"extra\":[")) {
            gson = new GsonBuilder().registerTypeAdapter(ChatMessage.class,
                    new ChatMessageDe()).create();
        } else {
            gson = new Gson();
        }
        if (message.length() < 5)
            return "";
        ChatMessage mws = gson.fromJson(message, ChatMessage.class);
        // ChatMessage mws = new Gson().fromJson(message, ChatMessage.class);
        if (!mws.getWith().isEmpty()) {
            List<Object> withs = mws.getWith();
            ChatMessage with = ((With) withs.get(0)).getNonNull(mws);
            username = with.getText();
            with.setText("<" + username + "> " + withs.get(1));
            formated = with.getText();
        } else if (!mws.getExtra().isEmpty()) {
            // String messag = "§" + attributes.get(mws.getColor()) +
            // mws.getText();
            String messag = "";
            String userb = "";
            for (int i = 0; i < mws.getExtra().size(); i++) {
                Object j = mws.getExtra().get(i);
                if (j instanceof String) {
                    messag += j;
                    userb += j;
                } else {
                    messag += ChatColor.COLOR_CHAR
                            + attributes.get(((Node) mws.getExtra().get(i))
                                    .getColor())
                            + ((Node) mws.getExtra().get(i)).getText();
                    userb += ((Node) mws.getExtra().get(i)).getText();
                }
            }
            username = getUsername(userb);
            formated = messag;
        }

        c.ehandle.handleEvent(new Event("onChatMessage", new Object[] {
                username, formated }));
        getCommandText(formated, username);
        c.gui.addText(formated);
        return mess;
    }

    /*
     * public String formatMessage(String message){ String mess =
     * "Something went wrong"; c.gui.addText(message); try{ JSON jsonr =
     * JSONSerializer.toJSON(message); if(!jsonr.isArray()){ JSONObject json =
     * (JSONObject) jsonr; if(json.containsKey("translate")){ String key =
     * json.getString("translate"); if(key.equalsIgnoreCase("chat.type.text")){
     * JSONArray arr = json.getJSONArray("with"); formatWith(json, arr); } }else
     * if(json.containsKey("extra")){ formatExtra(json); } }else{ //TODO add
     * method to parse this } }catch(JSONException ex){
     * c.gui.addText("§4Invalid json received; string skipped");
     * err.log(Level.WARNING, "MESSAGE: " + message +
     * " IS NOT VALID JSON, SKIPPING STRING..."); } return mess; }
     */

    // Code to attempt to get the username

    public String getUsername(String formated) {
        int delimiter = formated.indexOf(c.chatdelimiter);
        String user = "";
        String username = "";
        if (delimiter > 0) {
            user = formated.substring(0, delimiter);
            for (Entry<UUID, PlayerL> pl : c.plist.players.entrySet()) {
                if (isContain(user, pl.getValue().getName())) {
                    username = pl.getValue().getName();
                }
            }
        }
        return username;
    }

    // Credits to Jaskey from stackoverflow for this
    private boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }

    // Code to get the command from the string then pass it onto the command
    // handler
    public void getCommandText(String formated, String username) {
        if (formated.contains(c.prefix)) {
            String commande = formated.substring(formated.indexOf(c.prefix));
            int d = commande.length();
            if (commande.indexOf(" ") != -1) {
                d = commande.indexOf(" ");
            }
            String command = commande.substring(0, d);
            commande.trim();
            c.chandle.processCommand(command.replace(c.prefix, ""), commande
                    .substring(d).split(" "), username);
        }
    }

    // Code for getting all the colours together for multicoloured strings
    public String formatColours(JSONArray arr) {
        String formated = "";
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).toString().contains("{")) {
                JSONObject ob = arr.getJSONObject(i);
                String key = ob.getString("color");
                String theText = ob.getString("text");
                formated = formated + "§" + attributes.get(key) + theText;
            } else {
                formated = formated + "§0" + arr.getString(i);
            }
        }
        return formated;
    }

}
