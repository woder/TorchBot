package me.woder.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.woder.bot.Client;

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
        login.put(1, new EncryptionRequest253(c));
        login.put(2, new LoginSuccess02(c));
        //Play packets
        play.put(0, new KeepAlive00(c));
        play.put(1, new JoinGame01(c));
        play.put(2, new ChatMessage02(c));
        play.put(3, new TimeUpdate03(c));
        play.put(4, new EntityEquipment04(c));
        play.put(5, new SpawnPos05(c));
        play.put(8, new PlayerPosLook08(c));
        play.put(9, new HeldItemChange09(c));
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
        play.put(28, new EntityMetaData28(c));
        play.put(32, new EntityProperties32(c));
        play.put(38, new MapBulkChunk38(c));
        play.put(41, new SoundEffect41(c));
        play.put(43, new ChangeGameState43(c));
        play.put(47, new SetSlot47(c));
        play.put(48, new WindowItems48(c));
        play.put(53, new UpdateBlockEntity53(c));
        play.put(55, new Statistics55(c));
        play.put(56, new PlayerListItem56(c));
        play.put(57, new PlayerAbilites57(c));
        play.put(63, new PluginMessage63(c));
    }
    
    public void readData() throws IOException{
        int len = Packet.readVarInt(c.in);
        int type = Packet.readVarInt(c.in);
        log.log(Level.FINE, "Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        System.out.println("Reading packet id: " + type + " current state is: " + c.state + " packet length: " + len);
        if(c.state == 1){
            Packet p = status.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1);         
            }
        }else if(c.state == 2){
            Packet p = login.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1);         
            }
        }else if(c.state == 3){
            Packet p = play.get(type);
            if(p==null){c.in.read(trash, 0, len-1);System.out.println("NOTICE: we just threw out a packet");}else{
                p.read(c, len - 1);         
            }
        }else{
            throw new IOException("Unknown type!");
        }

    }
    
    /*(@SuppressWarnings("unused")
    public void readOldData() throws IOException{
             int idd = (in.readByte() & 0xff);
             log.log(Level.FINE, "Reading packet: " + idd);
             switch(idd){
              case 0:
                 int randomint = in.readInt();
                 out.writeByte(0);
                 out.writeInt(randomint);
                 out.flush();
                 log.log(Level.FINEST,"Keep alive: " + randomint);
                 break;
              case 1:
                 c.entityID = in.readInt();
                 short sl = in.readShort();
                 c.leveltype = getString(in, sl, 30);
                 log.log(Level.FINEST,"Level type: " + c.leveltype);
                 Client.gamemode = in.readByte();
                 log.log(Level.FINEST,"Gamemode: " + Client.gamemode);
                 c.dimension = in.readByte();
                 log.log(Level.FINEST,"Dimension: " + c.dimension);
                 c.difficulty = in.readByte();
                 short height = (short) (in.readByte() & 0xff);
                 short maxplayer = (short) (in.readByte() & 0xff);
                break;
              case 2:
                  byte b = in.readByte();
                  short sh = in.readShort();
                  String lo = getString(in,sh,300);
                  short sa = in.readShort();
                  String la = getString(in,sa,300);
                  int port1 = in.readInt();
                  break;
              case 3:
                  c.chat.readMessage();
                  break;
              case 4:
                  c.age = in.readLong();
                  c.time = in.readLong();
                  break;
              case 5:
                  in.readInt();
                  new Slot(in, in.readShort());
                  break;
              case 6:
                  int X4 = in.readInt();
                  int Y4 = in.readInt();
                  int Z4 = in.readInt();
                  break;
              case 8:
                  c.health = in.readFloat();
                  c.food = in.readShort();
                  c.foodsat = in.readFloat();
                  break;
              case 9:
                  in.readInt();
                  in.readByte();
                  in.readByte();
                  in.readShort();
                  c.chat.readString();
                  break;
              case 13:
                  //receive the data
                  double X = in.readDouble();
                  double Y = in.readDouble();
                  double Stance = in.readDouble();
                  c.stance = Stance;
                  double Z = in.readDouble();
                  float Yaw = in.readFloat();
                  float Pitch = in.readFloat();
                  byte onGround = in.readByte();
                  log.log(Level.FINEST,"Location is: " + X + "," + Y + "," + Z + " stance is" + Stance);
                  c.location = new Location(c.whandle.getWorld(), X, Y, Z);
                  //c.chat.sendMessage("Location updated to: " + X + "," + Y + "," + Z);
                  //send it back
                  out.writeByte(0x0D);
                  out.writeDouble(X);
                  out.writeDouble(Stance);
                  out.writeDouble(Y);
                  out.writeDouble(Z);                   
                  out.writeFloat(Yaw);
                  out.writeFloat(Pitch);
                  out.writeByte(onGround);
                  out.flush();                  
                  break;
              case 15:
                  in.readInt();
                  in.readByte();
                  in.readInt();
                  in.readByte();
                  new Slot(in, 0);
                  in.readByte();
                  in.readByte();
                  in.readByte();
                  break;
              case 16:
                 short inhand = in.readShort();
                 break;
              case 18:
                  int eidd = in.readInt();
                  byte animation = in.readByte();
                  break;
              case 19:
                  in.readInt();
                  in.readByte();
                  in.readInt();
                  break;
              case 20:
                  c.entities.add(new Player(c));
                  break;
              case 22:
                  int pickup = in.readInt();
                  int pickup1 = in.readInt();
                  break;
              case 23:
                  c.entities.add(new EntityVehicle(c));
                  break;
              case 24:
                  new SpawnEntity(in, c);
                  break;
              case 25:
                  in.readInt();
                  c.chat.readString();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  break;
              case 26:
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readShort();
                  break; 
              case 28:
                  in.readInt();
                  in.readShort();
                  in.readShort();
                  in.readShort();
                  break;
              case 29:
                 byte clans = in.readByte();
                 for(int i = 0; i < clans; i++){
                     in.readInt();
                 }
              break;
              case 31:
                 in.readInt();
                 in.readByte();
                 in.readByte();
                 in.readByte();
                 break;
              case 32:
                 in.readInt();
                 in.readByte();
                 in.readByte();
              break;
              case 33:
                 Entity e = c.findEntityId(in.readInt());
                if(e != null){
                   Location ls = e.getALocation(); 
                   double lx, ly, lz; 
                   lx = ls.getX();
                   ly = ls.getY();
                   lz = ls.getZ();              
                   e.updateLocation(new Location(c.whandle.getWorld(), lx += in.readByte(), ly += in.readByte(), lz += in.readByte()));
                }else{
                 in.readByte();
                 in.readByte();
                 in.readByte();
                }
                 in.readByte();
                 in.readByte();
                 break;
              case 34:
                 Entity es = c.findEntityId(in.readInt());
                if(es != null){es.updateLocation(new Location(c.whandle.getWorld(), in.readInt(), in.readInt(), in.readInt()));}else{
                 in.readInt();
                 in.readInt();
                 in.readInt();
                }
                 in.readByte();
                 in.readByte();
                 break;
              case 35:
                 in.readInt();
                 in.readByte();
                 break;
              case 36:
                 int x1 = in.readInt();
                 byte y1 = in.readByte();
                 int z1 = in.readInt();
                 byte b1 = in.readByte();
                 byte b2 = in.readByte();
                 short bid = in.readShort();
                 break;
              case 38:
                  if(in.readInt() == c.entityID){
                    if(in.readByte() == 3){
                        out.writeByte(0xCD);
                        out.writeByte(1);
                        out.flush();
                    }
                  }else{
                      in.readByte();
                  }
                  break;
              case 39:
               in.readInt();
               in.readInt();
               in.readByte();
               break;
              case 40:
                  int entityid = in.readInt();
                  //new EntityMetaData(in, entityid);
                  c.proc.readWatchableObjects(in);
                  break;
              case 41:
                  int entityide = in.readInt();
                  byte effectid = in.readByte();
                  byte amp = in.readByte();
                  short duration = in.readShort();
                  break;
              case 43:
                  c.exp = in.readFloat();
                  c.level = in.readShort();
                  c.lvlto = in.readShort();
                  break;
              case 44:
                  int eide = in.readInt();
                  int pcount = in.readInt();
                  String[] key = new String[pcount];
                  for(int i = 0; i<pcount;i++){
                      short strs = in.readShort();
                      key[i] = getString(in, strs, 300);
                      log.log(Level.FINEST,key[i]);
                      double value = in.readDouble();
                      short listLength = in.readShort();    
                     for (int z = 0; z < listLength; z++) {
                        long msb = in.readLong();
                        long lsb = in.readLong();
                        double ammount = in.readDouble();
                        byte op = in.readByte();
                     }
                  }                 
                  break;
              case 51:
                  new BChunk(in,c);
                  c.chunksloaded = true;
                  break;
              case 52:
                  in.readInt();
                  in.readInt();
                  in.readShort();
                  int var4 = in.readInt();
                  byte[] vas = new byte[var4];
                  in.readFully(vas);
                  break;
              case 53:
                  int bx = in.readInt();
                  byte by =in.readByte();
                  int bz = in.readInt();
                  short id = in.readShort();
                  byte meta = in.readByte();
                  c.chat.sendMessage("Meta data is: " + meta + " bid " + id);
                  c.whandle.getWorld().setBlock(bx, by, bz, id, meta);
                  break;
              case 54:
                  in.readInt();
                  in.readShort();
                  in.readInt();
                  in.readByte();
                  in.readByte();
                  in.readShort();
                  break;
              case 55:
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readByte();
                  break;
              case 56:
               new PChunk(in, c);
               c.chunksloaded = true;
                  break;
              case 61:
                  int effect = in.readInt();
                  int x = in.readInt();
                  byte y = in.readByte();
                  int z = in.readInt();
                  int data = in.readInt();
                  boolean volume = in.readBoolean();
                  break;    
              case 62:
                  String soundname = c.chat.readString();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  in.readFloat();
                  in.readByte();
                  break;
              case 70:
                 byte reason = in.readByte();
                 byte gamemode = in.readByte();
                 break;
              case 71:
                  in.readInt();
                  in.readByte();
                  in.readInt();
                  in.readInt();
                  in.readInt();
                  break;
              case 100:
                  byte wid = in.readByte();
                  in.readByte();
                  short val = in.readShort();
                  log.log(Level.FINEST,getString(in, val, 100));
                  in.readByte();
                  in.readBoolean();
                  if(wid == 11)in.readInt();
                  break;
              case 103:
                  byte winde = in.readByte();
                  short slo = in.readShort();
                  log.log(Level.FINEST,"Slot id is: " + slo);
                 if(slo != -1){
                  if(c.inventory.size() >= slo){
                   //inventory.remove(slo);
                  }
                  new Slot(in, slo);
                   //inventory.add(slo,new Slot(in, slo));
                 }else{
                    new Slot(in, slo);
                 }
                  break;
              case 104:
                  byte winda = in.readByte();
                  short count = in.readShort();
                  for(int i = 0; i < count;i++){
                      c.inventory.add(new Slot(in,i));
                  }
                  break;
              case 101:
                  byte windo = in.readByte();
                  break;
              case 105:
                  in.readByte();
                  in.readShort();
                  in.readShort();
                  break;
              case 108:
                  byte window = in.readByte();
                  byte enchant = in.readByte();
                  break;     
              case 130:
                 int sx = in.readInt(); 
                 short sy = in.readShort();
                 int sz = in.readInt();
                 short le = in.readShort();
                 String text1 = getString(in, le, 16);
                 le = in.readShort();
                 String text2 = getString(in, le, 16);
                 le = in.readShort();
                String text3 = getString(in, le, 16);
                le = in.readShort();
                 String text4 = getString(in, le, 16);
                 log.log(Level.FINEST,"Sign at " + sx + "," + sy + "," + sz + " now says: " + text1 + ";" + text2 + ";" + text3 + ";" + text4);
                 break;
              case 131:
                  short itype = in.readShort();
                  short iidd = in.readShort();
                  byte[] text = c.readBytesFromStream(in);
                  break;
              case 132:
                  int ex = in.readInt();
                  short ey = in.readShort();
                  int ez = in.readInt();
                  byte action = in.readByte();
                  short dn = in.readShort();
                  if(dn > 0){
                      byte[] ed = new byte[dn];
                      in.readFully(ed);
                  }
                  break;
              case 200:
                  in.readInt();
                  in.readInt();
                  break;
              case 201:
                 short str2 = in.readShort();
                 String user = getString(in, (int)str2, 16);
                 byte bool = in.readByte();
                 short ping = in.readShort();
                 log.log(Level.FINEST,"User: " + user);
                 break;
              case 202://PlayerAbilities
                 c.flags = in.readByte();
                 c.flyspeed = in.readFloat();
                 c.walkspeed = in.readFloat();
                 break;
              case 206:
                 c.chat.readString();
                 c.chat.readString();
                 in.readByte();
                 break;
              case 207:
                 new UpdateScore207().read(c);
                 break;
              case 208:
                 in.readByte();
                 c.chat.readString();
                 break;
              case 209:
                 new TeamPacket209().read(c);
                 break;
              case 250: //Plugin message
                 short l = in.readShort();
                 String channel = getString(in, l, 300);
                 log.log(Level.FINEST,"Plugin message: " + channel);
                 byte[] info = c.readBytesFromStream(in);
                 break;
              case 252:
                  byte[] empty1 = c.readBytesFromStream(in);
               byte[] empty2 = c.readBytesFromStream(in);
               c.activateEncryption();
               c.decryptInputStream();
               this.out = c.out;
               this.in = c.in;
               out.writeByte(0xCD);
               out.writeByte(0);
               out.flush();
                  break;
              case 253:
                 short howlong = in.readShort();
                 String serverid = getString(in, (int)howlong, 300);//read the server id
                 log.log(Level.FINEST,"Reading server id: " + serverid);
                 c.publickey = CryptManager.decodePublicKey(c.readBytesFromStream(in));//read the public key**taken from the original minecraft code**
                 byte[] verifytoken = c.readBytesFromStream(in);//read the verify token        
                 c.secretkey = CryptManager.createNewSharedKey();//generate a secret key
                 c.sharedkey = c.secretkey;
                 log.log(Level.FINEST,"Secret key is: " + c.secretkey);
                 String var5 = (new BigInteger(CryptManager.getServerIdHash(serverid.trim(), c.publickey, c.secretkey))).toString(16);
                 String var6 = c.sendSessionRequest(c.username, "token:" + c.accesstoken + ":" + c.profile, var5);
                 log.log(Level.FINEST,var6);
                 byte[] sharedSecret = new byte[0];
                 byte[] verifyToken = new byte[0];
                 out.writeByte(0xFC);//send an encryption response
                 sharedSecret = CryptManager.encryptData(c.publickey, c.secretkey.getEncoded());
                 verifyToken = CryptManager.encryptData(c.publickey, verifytoken);
                 c.writeByteArray(out, sharedSecret);//send 
                 c.writeByteArray(out, verifyToken);
                 out.flush();
                 break;
              case 255:
                short length = in.readShort();
                if(length > 0){
                String reasons = getString(in, (int) length, 300);
                c.gui.addText("§4You were kicked: " + reasons);
                log.log(Level.WARNING,"You were kicked: " + reasons);
                }
                out.close();
                in.close();
                c.running = false;
                c.clientSocket.close();
                break;
              default:
                log.log(Level.SEVERE,"We received unhandled packet: " + idd);  
                throw new IOException("Unhandled packet!");
              }                 
    }*/

}
