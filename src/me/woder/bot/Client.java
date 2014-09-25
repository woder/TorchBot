package me.woder.bot;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.output.StringBuilderWriter;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.event.EventHandler;
import me.woder.gui.TorchGUI;
import me.woder.irc.IRCBridge;
import me.woder.network.ByteArrayDataInputWrapper;
import me.woder.network.NetworkHandler;
import me.woder.network.Packet;
import me.woder.plugin.PluginLoader;
import me.woder.world.Location;
import me.woder.world.World;
import me.woder.world.WorldHandler;


public class Client {
    public TorchGUI gui; //TODO fix this mess of a variable declaration
    public EventHandler ehandle;
    public ChatHandler chat;
    public MetaDataProcessor proc;
    public PluginLoader ploader;
    public CommandHandler chandle;
    public WorldHandler whandle;
    public MovementHandler move;
    public InvHandler invhandle;
    public NetworkHandler net;
    public EntityTracker en;
    public IRCBridge irc;
    public DataOutputStream out;
    public DataInputStream in;
    public PublicKey publickey;
    public SecretKey secretkey;
    public SecretKey sharedkey;
    public Authenticator auth;
    public ServerPinger servping;
    Socket clientSocket;
    boolean isInputBeingDecrypted;
    boolean isOutputEncrypted;
    public String prefix;
    public byte gamemode;
    String leveltype;
    public Location location;
    World world;
    public boolean chunksloaded;
    public boolean connectedirc;
    public int entityID;
    public byte dimension;
    public byte difficulty;
    public byte maxplayer;
    public byte flags;
    public float flyspeed;
    public float walkspeed;
    public long time;
    public long age;
    public float health;
    public int food;
    public float foodsat;
    public float exp;
    public short level;
    public short lvlto;
    public double stance;
    public String username;
    int port;
    String servername;
    String sessionId;
    public int protocol;
    public int state = 2;
    public boolean running = true;
    private String password;
    public int mcversion = 47;
    public int threshold = 0;
    public String accesstoken;
    public String clienttoken;
    public String profile;
    public String version = "0.3";
    public String versioninfo = "TorchBot version " + version + " by woder";
    public File[] plugins = null;
    public String[] cmds = null;
    public String[] descriptions = null;
    public byte selectedslot;
    public boolean onground;
    public float yaw;
    public float pitch;
    public List<SlotHandler> inventory = new ArrayList<SlotHandler>();
    public boolean ircenable = false;
    //Credits to umby24 for the help, Thinkofdeath for help and SirCmpwn for Craft.net
    Logger netlog = Logger.getLogger("me.woder.network");
    Logger chatlog = Logger.getLogger("me.woder.chat");
    Logger errlog = Logger.getLogger("me.woder.network"); //doesn't seem right - check why error log is being saved to network log   
    
    public void main(TorchGUI window){
        this.gui = window;
        File f = new File("config.properties");
        if(f.exists()){
            Properties prop = new Properties();                
            try {
                prop.load(new FileInputStream("config.properties"));
                username = prop.getProperty("username");
                password = prop.getProperty("password");
                servername = prop.getProperty("servername");
                ircenable = Boolean.valueOf(prop.getProperty("irc"));
                System.out.println(servername);
                port = Integer.parseInt(prop.getProperty("port"));
         
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }else{
            Properties prop = new Properties();             
            try {
                prop.setProperty("username", "unreal34");
                prop.setProperty("password", "1234");
                prop.setProperty("servername", "c.mcblocks.net");
                prop.setProperty("port", "25565");
                prop.setProperty("irc", "false");
                prop.store(new FileOutputStream("config.properties"), null);
         
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
        Handler fh = null, fn = null, fe = null;
        new File("logs").mkdir();
        try {
            fh = new FileHandler("logs/network.log");
            fn = new FileHandler("logs/chat.log");
            fe = new FileHandler("logs/err.log");
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Logger.getLogger("me.woder.network").addHandler(fh);
        Logger.getLogger("me.woder.chat").addHandler(fn);
        Logger.getLogger("me.woder.error").addHandler(fe);
        Logger.getLogger("me.woder.network").setLevel(Level.FINEST);
        Logger.getLogger("me.woder.chat").setLevel(Level.FINEST);
        Logger.getLogger("me.woder.error").setLevel(Level.FINEST);
        prefix = "!";
        gui.addText("§3Welcome to TorchBot " + version + ", press the connect button to connect to the server defined in config");
        gui.addText("§3 or press the change server button to login to a new server.");   
        auth = new Authenticator(this);//declare our new objects
        servping = new ServerPinger(this);
        auth.authPlayer(username, password);//use them to do important things
        servping.pingServer(servername, port);      
        ploader = new PluginLoader(this);
        ploader.loadPlugins();
        gui.pradar.dbot.updateName(username);
    }
    
    public void startBot(String server, String port){
       this.servername = server;
       try{
         this.port = Integer.parseInt(port);
       }catch(NumberFormatException e){
         netlog.log(Level.SEVERE, "§4Port was not an integer!");
         gui.addText("§4Port was not an integer!");
       }
       startBot();
    }
    
    public void startBot(){
        chunksloaded = false;
        connectedirc = false;
        running = true;
        try{
          // open a socket
        System.out.println("Attempting to connect to: " + servername + " on " + port);
        gui.addText("§3Attempting to connect to: " + servername + " on " + port);
        clientSocket = new Socket(servername, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
        
        net = new NetworkHandler(this);//this needs to be done first because we need this to send stuff
        //our hand shake

        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 0);
        Packet.writeVarInt(buf, mcversion);
        Packet.writeString(buf, servername);
        buf.writeShort(port);
        Packet.writeVarInt(buf, 2);
        
        net.sendPacket(buf, out);
        
        buf = ByteStreams.newDataOutput();
        
        Packet.writeVarInt(buf, 0);
        Packet.writeString(buf, username);

        net.sendPacket(buf, out);
        
        out.flush();                             
         
        chat = new ChatHandler(this);
        ehandle = new EventHandler(this);
        proc = new MetaDataProcessor(this);
        chandle = new CommandHandler(this);
        whandle = new WorldHandler(this);               
        en = new EntityTracker(this);
        world = whandle.getWorld();
        invhandle = new InvHandler(this);
        location = new Location(world, 0, 0, 0);
        move = new MovementHandler(this);
        /*irc = new IRCBridge(this);
        if(ircenable){
           irc.start();
        }*/
        int tick = 0;
        while(running){
           tick+= 1;
           //mainloop
           net.readData();//Read data
           gui.tick();
           gui.pradar.dbot.updateText(username, location.getBlockX(), location.getBlockY(), location.getBlockZ());
           en.tickRadar();
           if(chunksloaded){
             if(tick == 5){
                tick = 0;
                //move.applyGravity();//Apply gravity
             }
            move.tick();
            //move.sendOnGround();
           }
           if(tick == 5){
               tick = 0;
           }
        }
         
      }catch (Exception e){
          e.printStackTrace();
      }
    }
    
    public void stopBot(){
        try {
            running = false;
            out.close();
            in.close();
            clientSocket.close();
            this.state = 2;
            threshold = 0;
            chat = null;
            ehandle = null;
            proc = null;
            chandle = null;
            whandle = null;               
            en = null;
            world = null;
            invhandle = null;
            location = null;
            move = null;
        } catch (IOException e) {
            gui.addText("§4Unable to disconnect! Weird error.. (check network log)");
            netlog.log(Level.SEVERE, "UNABLE TO DISCONNECT: " + e.getMessage());
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

     
    public String sendSessionRequest(String user, String session, String serverid){
        try {
            return sendGetRequest("http://session.minecraft.net/game/joinserver.jsp?user=" + urlEncode(user) + "&sessionId=" + urlEncode(session) + "&serverId=" + urlEncode(serverid));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String toString(InputStream input)throws IOException{
            StringBuilderWriter sw = new StringBuilderWriter();
            copy(input, sw, Charset.defaultCharset());
            return sw.toString();
    }
    
    public static void copy(InputStream input, Writer output, Charset encoding)throws IOException{
         InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(encoding));
         long count = copyLarge(in, output, new char[4096]);
         if (count > 2147483647L) {
            return;
         }
    }
    
    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException{
            long count = 0L;
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
              output.write(buffer, 0, n);
              count += n;
            }
            return count;
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
    
    public String sendGetRequest(String url){
        try
        {
            URL var4 = new URL(url);
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
    
    private static String urlEncode(String par0Str) throws IOException {
        return URLEncoder.encode(par0Str, "UTF-8");
    }
    
    
    public void writeByteArray(DataOutputStream par0DataOutputStream, byte[] par1ArrayOfByte) throws IOException{
        par0DataOutputStream.writeShort(par1ArrayOfByte.length);
        par0DataOutputStream.write(par1ArrayOfByte);
    }
     
    public byte[] readBytesFromStreamV(ByteArrayDataInputWrapper par0DataInputStream) throws IOException{
            int var1 = Packet.readVarInt(par0DataInputStream);
            System.out.println("Length is: " + var1);
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
    
    public byte[] readBytesFromStream(ByteArrayDataInputWrapper par0DataInputStream) throws IOException{
        int var1 = par0DataInputStream.readShort();
        System.out.println("Length is: " + var1);
        if (var1 < 0)
        {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        else
        {
            byte[] var2 = new byte[var1];
            par0DataInputStream.readFully(var2, 0, var1);
            return var2;
        }
    }
     

}
