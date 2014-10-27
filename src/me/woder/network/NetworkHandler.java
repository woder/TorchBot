package me.woder.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;

public class NetworkHandler {
    private Client c;
    private Logger log;
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
        play.put(70, new SetCompression70(c));
    }
    
    public void readData() throws IOException{
      if(c.threshold > 0){
        int plen1 = Packet.readVarInt(c.in);
        int[] dlens = Packet.readVarIntt(c.in);
        int dlen = dlens[0];
        int plen = plen1-dlens[1];
        if(dlen == 0){ //this packet isn't compressed
           readUncompressed(plen);
        }else{ //this packet is compressed
           readCompressed(plen, dlen);
        }
      }else{ //We aren't currently compressing anything
          readUncompressed();
      }

    }
    
    public void readUncompressed() throws IOException{
        int len1 = Packet.readVarInt(c.in);
        int[] types = Packet.readVarIntt(c.in);
        int type = types[0];
        int len = len1-types[1];
        byte[] data = new byte[len];
        c.in.readFully(data, 0, len);
        //the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
        forwardPacket(len, type, new ByteArrayDataInputWrapper(data)); //pass this on to the actual parser
    }
    
    public void readUncompressed(int len) throws IOException{
        byte[] data = new byte[len];
        c.in.readFully(data, 0, len);
        ByteArrayDataInputWrapper bf = new ByteArrayDataInputWrapper(data);
        int type = Packet.readVarInt(bf);
        //the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
        forwardPacket(len, type, bf); //pass this on to the actual parser
    }
    
    public void readCompressed(int plen, int dlen) throws IOException{
        if(dlen >= c.threshold){ //if the data length is less than we set in login packet 3, throw an error
            byte[] data = new byte[plen];
            c.in.readFully(data, 0, plen);
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            byte[] uncompressed = new byte[dlen];
            try{
               inflater.inflate(uncompressed);
            }catch(DataFormatException dataformatexception){
               dataformatexception.printStackTrace();
               throw new IOException("Bad compressed data format");
            }finally{
               inflater.end();
            }
            ByteArrayDataInputWrapper buf = new ByteArrayDataInputWrapper(uncompressed); //the ONLY reason we do this is because stupid minecraft made packets compress and changing it any other way means re-doing 68 packets
            int type = Packet.readVarInt(buf);
            forwardPacket(dlen, type, buf);
        }else{
           throw new IOException("Data was smaller than threshold!");
        }
    }
    
    public void forwardPacket(int len, int type, ByteArrayDataInputWrapper buf) throws IOException{
        //log.log(Level.FINE, "Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        //System.out.println("Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        if(c.state == 1){
            Packet p = status.get(type);
            if(p==null){System.out.println("NOTICE: we just threw out a packet; the id was: " + type);}else{
                p.read(c, len, buf);
            }
        }else if(c.state == 2){
            Packet p = login.get(type);
            if(p==null){System.out.println("NOTICE: we just threw out a packet; the id was: " + type);}else{
                p.read(c, len, buf);         
            }
        }else if(c.state == 3){
            Packet p = play.get(type);
            if(p==null){System.out.println("NOTICE: we just threw out a packet; the id was: " + type);}else{
                p.read(c, len, buf);         
            }
        }else{
            throw new IOException("Unknown type!");
        }
    }
    
    public void sendPacket(ByteArrayDataOutput buf, DataOutputStream out) throws IOException{
      if(c.threshold > 0){
        ByteArrayDataOutput send1 = ByteStreams.newDataOutput();
        Packet.writeVarInt(send1, 0);//do not compress... lol
        send1.write(buf.toByteArray());
        ByteArrayDataOutput send2 = ByteStreams.newDataOutput();
        Packet.writeVarInt(send2, send1.toByteArray().length);
        send2.write(send1.toByteArray());
        out.write(send2.toByteArray());
        out.flush();
      }else{
        ByteArrayDataOutput send1 = ByteStreams.newDataOutput();
        Packet.writeVarInt(send1, buf.toByteArray().length);
        send1.write(buf.toByteArray());
        out.write(send1.toByteArray());
        out.flush();
      }
    }
}
