package me.woder.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class NetworkHandler {
    private Client c;
    private Logger log;
    byte[] trash = new byte[1000000];
    HashMap<Integer, Packet> status = new HashMap<Integer, Packet>();
    HashMap<Integer, Packet> login = new HashMap<Integer, Packet>();
    HashMap<Integer, Packet> play = new HashMap<Integer, Packet>();
    
    public NetworkHandler(Client c){
        log = Logger.getLogger("me.woder.network");
        this.c = c;
        //Login packets
        login.put(0, new Disconnect00(c));
        login.put(1, new EncryptionRequest253(c));
        login.put(2, new LoginSuccess02(c));
        login.put(3, new SetCompression03(c));
        //Play packets
        play.put(0, new KeepAlive00(c));
        play.put(1, new JoinGame01(c));
        play.put(2, new ChatMessage02(c));
        play.put(3, new TimeUpdate03(c));
        play.put(4, new EntityEquipment04(c));
        play.put(5, new SpawnPos05(c));
        play.put(6, new HealthUpdate06(c));
        play.put(8, new PlayerPosLook08(c));
        play.put(9, new HeldItemChange09(c));
        play.put(11, new Animation11(c));
        play.put(12, new SpawnPlayer12(c));
        play.put(14, new SpawnObject14(c));
        play.put(15, new SpawnMob15(c));
        play.put(18, new EntityVelocity18(c));
        play.put(19, new DestroyEntities19(c));
        play.put(21, new EntityRelativeMove21(c));
        play.put(22, new EntityLook22(c));
        play.put(23, new EntityRelativeMoveLok23(c));
        play.put(24, new EntityTeleport24(c));
        play.put(25, new EntityHeadLook25(c));
        play.put(26, new EntityStatus26(c));
        play.put(28, new EntityMetaData28(c));
        play.put(32, new EntityProperties32(c));
        play.put(33, new MapChunk33(c));
        play.put(35, new BlockChange35(c));
        play.put(38, new MapBulkChunk38(c));
        play.put(40, new Effect40(c));
        play.put(41, new SoundEffect41(c));
        play.put(43, new ChangeGameState43(c));
        play.put(47, new SetSlot47(c));
        play.put(48, new WindowItems48(c));
        play.put(51, new UpdateSign51(c));
        play.put(53, new UpdateBlockEntity53(c));
        play.put(55, new Statistics55(c));
        play.put(56, new PlayerListItem56(c));
        play.put(57, new PlayerAbilites57(c));
        play.put(61, new UpdateScore61(c));
        play.put(62, new TeamPacket62(c));
        play.put(63, new PluginMessage63(c));
        play.put(64, new Disconnect64(c));
    }
    
    public void readData() throws IOException{
      if(c.threshold > 0){
        int plen = Packet.readVarInt(c.in);
        int dlen = Packet.readVarInt(c.in);
        System.out.println("Values: " + plen + " " + dlen);
        if(dlen == 0){ //this packet isn't compressed
           int type = Packet.readVarInt(c.in);
           System.out.println("Length?? " + dlen + " " + type);
           byte[] data = new byte[plen-1];
           c.in.read(data, 0, plen-1);
           ByteArrayDataInput buf = ByteStreams.newDataInput(data);//the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
           forwardPacket(plen, type, buf); //pass this on to the actual parser 
        }else{ //this packet is compressed ***BROKEN***
           if(dlen <= c.threshold){ //if the data length is more than we set in login packet 3, throw an error
               byte[] data = new byte[dlen];
               Inflater inflater = new Inflater();
               inflater.setInput(data);
               byte[] uncompressed = new byte[dlen];
               try{
                  System.out.println("De compressed " + inflater.inflate(uncompressed));
               }catch(DataFormatException dataformatexception){
                  throw new IOException("Bad compressed data format");
               }finally{
                  inflater.end();
               }
               ByteArrayDataInput buf = ByteStreams.newDataInput(data); //the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
               int type = Packet.readVarInt(buf);
               forwardPacket(dlen, type, buf);
           }else{
              throw new IOException("Data was smaller than threshold!");
           }
        }
      }else{ //We aren't currently compressing anything
        int len = Packet.readVarInt(c.in);
        int type = Packet.readVarInt(c.in);
        System.out.println("Length?? " + len + " " + type);
        byte[] data = new byte[len-1];
        c.in.read(data, 0, len-1);
        ByteArrayDataInput buf = ByteStreams.newDataInput(data);//the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
        forwardPacket(len, type, buf); //pass this on to the actual parser
      }

    }
    
    public void forwardPacket(int len, int type, ByteArrayDataInput buf) throws IOException{
        log.log(Level.FINE, "Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        System.out.println("Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        if(c.state == 1){
            Packet p = status.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1, buf);         
            }
        }else if(c.state == 2){
            Packet p = login.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1, buf);         
            }
        }else if(c.state == 3){
            Packet p = play.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1, buf);         
            }
        }else{
            throw new IOException("Unknown type!");
        }
    }
}
