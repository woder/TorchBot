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
import java.net.HttpURLConnection;
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

import javax.crypto.SecretKey;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.output.StringBuilderWriter;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import me.woder.gui.TorchGUI;
import me.woder.irc.IRCBridge;
import me.woder.world.Location;
import me.woder.world.World;
import me.woder.world.WorldHandler;


public class Client {
    public TorchGUI gui;
    public ChatHandler chat;
    public MetaDataProcessor proc;
    public CommandHandler chandle;
    public WorldHandler whandle;
    public MovementHandler move;
    public NetworkHandler net;
    public IRCBridge irc;
    public DataOutputStream out;
    public DataInputStream in;
    PublicKey publickey;
    SecretKey secretkey;
    SecretKey sharedkey;
    Socket clientSocket;
    boolean isInputBeingDecrypted;
    boolean isOutputEncrypted;
    public String prefix;
    public static byte gamemode;
    String leveltype;
    Location location;
    World world;
    boolean chunksloaded;
    boolean connectedirc;
    int entityID;
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
    String username = "";//TODO add way to change this
    String sessionId;
    private String password;
    public String accesstoken;
    public String clienttoken;
    public String profile;
    List<Slot> inventory = new ArrayList<Slot>();
    //List<Player> players = new ArrayList<Player>();//Is exclusive to players
    List<Entity> entities = new ArrayList<Entity>();//Includes players
    //Credits to umby24 for the help and SirCmpwn for Craft.net
    
    public Client(TorchGUI gui){
        this.gui = gui;
    }
    
    public void main(){
        String server = "";
            File f = new File("config.properties");
            if(f.exists()){
                Properties prop = new Properties();                
                try {
                    prop.load(new FileInputStream("config.properties"));
                    username = prop.getProperty("username");
                    password = prop.getProperty("password");
                    server = prop.getProperty("server");
         
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                Properties prop = new Properties();             
                try {
                    prop.setProperty("username", "unreal34");
                    prop.setProperty("password", "1234");
                    prop.setProperty("server", "smp.mcsteamed.net");
                    prop.store(new FileOutputStream("config.properties"), null);
         
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            prefix = "!";
            String testServerName = "localhost";
            int port = 25564;            
            //Code for login in to mc.net:
            //String code = sendPostRequest("user="+username+"&password="+password+"&version="+client_version, "https://login.minecraft.net/");
            authPlayer(username,password);
            //System.out.println(code);
            //String[] values = code.split(":");
            //sessionId = values[3];
            chunksloaded = false;
            connectedirc = false;
            try{
              // open a socket
            clientSocket = new Socket(server, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            //our hand shake
            int str = username.length();
            out.writeByte(0x02);
            out.writeByte(74);//74 = 1.6.2
            out.writeShort(str);
            out.writeChars(username);
            out.writeShort(testServerName.length());
            out.writeChars(testServerName);
            out.writeInt(port);
            out.flush();                             
             
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
    
    public Player findPlayer(String name){
        Player p = null;
        for(Entity s : entities){
           if(s.getEntity() instanceof Player){
            Player a = (Player)s;
            if(a.getName().equals(name)){
                p = a;
                break;
            }
           }
        }
        return p;
    }
    
    public Entity findEntityId(int id){
        Entity e = null;
        for(Entity s : entities){
            if(s.getEntityId() == id){
                e = s;
                break;
            }
        }
        return e;
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
            JSONObject json = (JSONObject) JSONSerializer.toJSON(toString(stream));  
            accesstoken = json.getString("accessToken");
            clienttoken = json.getString("clientToken");
            System.out.println(json.toString());
            profile = json.getJSONObject("selectedProfile").getString("id");
            System.out.println("So the dick is: " + hc.getResponseMessage() + " and the puss: " + accesstoken + " and er: " + clienttoken + " profile is " + profile);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
