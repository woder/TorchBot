package me.woder.bot;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import me.woder.gui.TorchGUI;
import me.woder.network.Packet;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.bouncycastle.util.encoders.Base64;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class ServerPinger {
    Client c;
    
    public ServerPinger(Client c){
       this.c = c;
    }
    
    @SuppressWarnings("unused")
    public void pingServer(String server, int port){
        Socket clientSocket = c.clientSocket;
        TorchGUI gui = c.gui;
        Logger netlog = c.netlog;
        try {
            clientSocket = new Socket(server, port);
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            ByteArrayDataOutput buf = ByteStreams.newDataOutput();
            Packet.writeVarInt(buf, 0);
            Packet.writeVarInt(buf, 4);
            Packet.writeString(buf, c.servername);
            buf.writeShort(port);
            Packet.writeVarInt(buf, 1);          
            Packet.sendPacket(buf, out);
            
            buf = ByteStreams.newDataOutput();
            Packet.writeVarInt(buf, 0);
            Packet.sendPacket(buf, out);
            
            Packet.readVarInt(in);
            int id = Packet.readVarInt(in);
            
            if(id == 0){
                String pings = Packet.getString(in);
                System.out.println("Pings: " + pings);
                JSONObject json = (JSONObject) JSONSerializer.toJSON(pings);  
                JSONObject version = json.getJSONObject("version");
                String prot = version.getString("name");
                String ver = version.getString("protocol");
                JSONObject players = json.getJSONObject("players");
                String max = players.getString("max");
                String online = players.getString("online");
                String text = json.getString("description");
                if(json.containsKey("favicon")){
                   String images = json.getString("favicon").replace("data:image/png;base64,", "");
                   byte[] data = Base64.decode(images);
                   InputStream is = new ByteArrayInputStream(data);
                   ImageIcon test = new ImageIcon(data);
                   //gui.favicon.setIcon(test);
                   //gui.repaint();                
                }
                gui.addText("§5Game version: " + ver + "  " + text + " " + online + "/" + max);
            }
          out.close();
          in.close();
          clientSocket.close();
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            netlog.log(Level.SEVERE, "CONNECTION ERROR: " + e.getMessage());
            gui.addText("§4CONNECTION ERROR: " + e.getMessage());
            gui.addText("§4Check server info: " + server + " on " + port);
            
        }
    }    
}
