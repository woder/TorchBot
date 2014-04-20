package me.woder.bot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Authenticator {
    Client c;
    
    public Authenticator(Client c){
        this.c = c;
    }
    
    public String authPlayer(String user, String password){
        HttpURLConnection hc = null;
        try {
            URL var4 = new URL("https://authserver.mojang.com/authenticate");
            hc = (HttpURLConnection) var4.openConnection();
            hc.setRequestProperty("content-type","application/json; charset=utf-8"); 
            hc.setRequestMethod("POST");
            hc.setDoInput(true);
            hc.setDoOutput(true);
            hc.setUseCaches(false); 
            OutputStreamWriter wr = new OutputStreamWriter(hc.getOutputStream());
            JSONObject data = new JSONObject();
            JSONObject agent = new JSONObject();
            agent.put("name", "minecraft");
            agent.put("version", "1");
            data.put("agent", agent);
            data.put("username",user);
            data.put("password", password);
            System.out.println(data.toString());
            wr.write(data.toString());
            wr.flush();
            InputStream stream = null;
            try {
              stream = hc.getInputStream();
            }           
            catch (IOException e) {
               //TODO er... handle this?
               e.printStackTrace();
            }
            JSONObject json = (JSONObject) JSONSerializer.toJSON(Client.toString(stream));  
            c.accesstoken = json.getString("accessToken");
            c.clienttoken = json.getString("clientToken");
            System.out.println(json.toString());
            c.profile = json.getJSONObject("selectedProfile").getString("id");
            c.username = json.getJSONObject("selectedProfile").getString("name");//Get all of our data of here and to where it belongs
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
