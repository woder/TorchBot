package me.woder.bot;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKey;

import me.woder.irc.IRCBridge;
import me.woder.world.Location;
import me.woder.world.World;
import me.woder.world.WorldHandler;


public class Client {
    public ChatHandler chat;
    public MetaDataProcessor proc;
    public CommandHandler chandle;
    public WorldHandler whandle;
    public MovementHandler move;
    public NetworkHandler net;
    public IRCBridge irc;
    PublicKey publickey;
    SecretKey secretkey;
    SecretKey sharedkey;
    Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;
    boolean isInputBeingDecrypted;
    boolean isOutputEncrypted;
    public String prefix;
    String leveltype;
    Location location;
    World world;
    boolean chunksloaded;
    boolean connectedirc;
    int entityID;
    byte gamemode;
    byte dimension;
    byte difficulty;
    byte maxplayer;
    byte flags;
    float flyspeed;
    float walkspeed;
    long time;
    long age;
    float health;
    short food;
    float foodsat;
    float exp;
    short level;
    short lvlto;
    double stance;
    String username = "Unreal34";//TODO add way to change this
    String sessionId;
    List<Slot> inventory = new ArrayList<Slot>();
    //Credits to umby24 for the help and SirCmpwn for Craft.net
    
    @SuppressWarnings("unused")
    public void main(){
            prefix = "!";
            String testServerName = "localhost";
            int client_version = 9999;
            int port = 25565;            
            String password = "";//TODO add way to change this, config? make this private to stop plugins from acessing it
            //Code for login in to mc.net:
            String code = sendPostRequest("user="+username+"&password="+password+"&version="+client_version, "https://login.minecraft.net/");
            System.out.println(code);
            String[] values = code.split(":");
            sessionId = values[3];
            chunksloaded = false;
            connectedirc = false;
            try{
              // open a socket
            clientSocket = new Socket(testServerName, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            //our hand shake
           // String username2 = username1 + ";" + testServerName + ":" + port;
            int str = username.length();
            out.writeByte(0x02);
            out.writeByte(74);//74 = 1.6.2
            out.writeShort(str);
            out.writeChars(username);
            out.writeShort(testServerName.length());
            out.writeChars(testServerName);
            out.writeInt(port);
            out.flush();    
                  
             Random random = new Random();
             boolean working = true;
             boolean already = true;           
             
             chat = new ChatHandler(this);
             proc = new MetaDataProcessor(this);
             chandle = new CommandHandler(this);
             whandle = new WorldHandler(this);
             net = new NetworkHandler(this,in,out);          
             world = whandle.getWorld();
             move = new MovementHandler(this);
             irc = new IRCBridge(this);
             
             while(true){
                //mainloop
                net.readData();//Read data
               if(chunksloaded){
                //move.applyGravity();//Apply gravity
               }
               if(connectedirc){
                   irc.read();//Read text from irc, if there is some
               }
             }
             
          }catch (Exception e){
              e.printStackTrace();
          }
    }
    
    public void activateEncryption(){
        try {
            this.out.flush();
            this.isOutputEncrypted = true;
            BufferedOutputStream var1 = new BufferedOutputStream(CryptManager.encryptOuputStream(this.sharedkey, this.clientSocket.getOutputStream()), 5120);
            this.out = new DataOutputStream(var1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void decryptInputStream(){
        this.isInputBeingDecrypted = true;
        try {
             InputStream var1;
             var1 = this.clientSocket.getInputStream();
             this.in = new DataInputStream(CryptManager.decryptInputStream(this.sharedkey, var1));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     
    public String sendSessionRequest(String user, String session, String serverid)
    {
        try
        {
            URL var4 = new URL("http://session.minecraft.net/game/joinserver.jsp?user=" + urlEncode(user) + "&sessionId=" + urlEncode(session) + "&serverId=" + urlEncode(serverid));
            BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.openStream()));
            String var6 = var5.readLine();
            var5.close();
            return var6;
        }
        catch (IOException var7)
        {
            return var7.toString();
        }
    }
    
    public static String sendPostRequest(String data, String Adress) {
         
        String answer = "No";
         
            try {
                
                // Send the request
                URL url = new URL(Adress);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                
                //write parameters
                writer.write(data);
                writer.flush();
                
                // Get the response
                StringBuffer enswer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    enswer.append(line);
                }
                writer.close();
                reader.close();
                
                //Output the response
                
                answer = enswer.toString();
               
                
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return answer;
    }
    
    private static String urlEncode(String par0Str) throws IOException {
        return URLEncoder.encode(par0Str, "UTF-8");
    }
    
    
    public void writeByteArray(DataOutputStream par0DataOutputStream, byte[] par1ArrayOfByte) throws IOException
    {
        par0DataOutputStream.writeShort(par1ArrayOfByte.length);
        par0DataOutputStream.write(par1ArrayOfByte);
    }
     
     public byte[] readBytesFromStream(DataInputStream par0DataInputStream) throws IOException{
            short var1 = par0DataInputStream.readShort();

            if (var1 < 0)
            {
                throw new IOException("Key was smaller than nothing!  Weird key!");
            }
            else
            {
                byte[] var2 = new byte[var1];
                par0DataInputStream.readFully(var2);
                return var2;
            }
        }
     

}
