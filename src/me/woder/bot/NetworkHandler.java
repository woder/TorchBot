package me.woder.bot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import me.woder.world.Location;

public class NetworkHandler {
	private DataInputStream in;
	private DataOutputStream out;
	private Client c;
	
	public NetworkHandler(Client c, DataInputStream in, DataOutputStream out){
		this.in = in;
	    this.out = out;
	    this.c = c;
	}
	
	@SuppressWarnings("unused")
	public void readData() throws IOException{
   	      int idd = (in.readByte() & 0xff);
   	      System.out.println("Reading packet: " + idd);
   		  switch(idd){
   		   case 0:
   			  int randomint = in.readInt();
   			  out.writeByte(0);
   			  out.writeInt(randomint);
   			  out.flush();
   			 System.out.println("Keep alive: " + randomint);
   		      break;
   		   case 1:
         	   c.entityID = in.readInt();
         	   short sl = in.readShort();
  		       c.leveltype = getString(in, sl, 30);
  		       System.out.println("Level type: " + c.leveltype);
  		       c.gamemode = in.readByte();
  		       System.out.println("Gamemode: " + c.gamemode);
  		       c.dimension = in.readByte();
  		       System.out.println("Dimension: " + c.dimension);
  		       c.difficulty = in.readByte();
         	   short height = (short) (in.readByte() & 0xff);
    		   short maxplayer = (short) (in.readByte() & 0xff);
    		   c.chat.sendMessage("Sir, Bot reporting for duty.");
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
   			   c.chat.readMessage();
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
   			   System.out.println("Location is: " + X + "," + Y + "," + Z + " stance is" + Stance);
   			   c.location = new Location(c.whandle.getWorld(), X, Y, Z);
   			   c.chat.sendMessage("Location updated to: " + X + "," + Y + "," + Z);
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
   			   new Player(c);
   			   break;
   		   case 22:
   			   int pickup = in.readInt();
   			   int pickup1 = in.readInt();
   			   break;
   		   case 23:
   			   in.readInt();
   	           in.readByte();
   	           in.readInt();
   	           in.readInt();
   	           in.readInt();
   	           in.readByte();
   	           in.readByte();
   	           int valuee = in.readInt();
   	           if (valuee != 0){
   	            in.readShort();
   	            in.readShort();
   	            in.readShort();
   	           }
   			   break;
   		   case 24:
   			   new SpawnEntity(in, c);
   			   break;
   		   case 25:
   			   in.readInt();
   			   c.chat.readMessage();
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
   			  in.readInt();
   			  in.readByte();
   			  in.readByte();
   			  in.readByte();
   			  in.readByte();
   			  in.readByte();
   			  break;
   		   case 34:
   			  in.readInt();
   			  in.readInt();
   			  in.readInt();
   			  in.readInt();
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
   				   System.out.println(key[i]);
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
   			   in.readByte();
   			   c.whandle.getWorld().setBlock(bx, by, bz, id);
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
   			   String soundname = c.chat.readMessage();
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
   			   System.out.println(getString(in, val, 100));
   			   in.readByte();
   			   in.readBoolean();
   			   if(wid == 11)in.readInt();
   			   break;
   		   case 103:
   			   byte winde = in.readByte();
   			   short slo = in.readShort();
   			   System.out.println("Slot id is: " + slo);
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
   			  System.out.println("Sign at " + sx + "," + sy + "," + sz + " now says: " + text1 + ";" + text2 + ";" + text3 + ";" + text4);
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
  		       System.out.println("User: " + user);
  			   break;
   		   case 202://PlayerAbilities
  			   c.flags = in.readByte();
  		       c.flyspeed = in.readFloat();
  		       c.walkspeed = in.readFloat();
  			   break;
   		   case 250: //Plugin message
  			   short l = in.readShort();
  			   String channel = getString(in, l, 300);
  			   System.out.println("Plugin message: " + channel);
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
		      System.out.println("Reading server id: " + serverid);
		      c.publickey = CryptManager.decodePublicKey(c.readBytesFromStream(in));//read the public key**taken from the original minecraft code**
              byte[] verifytoken = c.readBytesFromStream(in);//read the verify token	    
              c.secretkey = CryptManager.createNewSharedKey();//generate a secret key
              c.sharedkey = c.secretkey;
              System.out.println("Secret key is: " + c.secretkey);
              String var5 = (new BigInteger(CryptManager.getServerIdHash(serverid.trim(), c.publickey, c.secretkey))).toString(16);
              String var6 = c.sendSessionRequest(c.username, c.sessionId, var5);
              System.out.println(var6);
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
   			 System.out.println("Something is triggering some strange shiz isn't it");
   			 short length = in.readShort();
   		     if(length > 0){
      	      String reasons = getString(in, (int) length, 300);
      	      System.out.println("You were kicked: " + reasons);
   		     }
      	      out.close();
      	      in.close();
      	      c.clientSocket.close();
      	      break;
   		   default:
   			 System.out.println("We received unhandled packet: " + idd);  
   			 throw new IOException("Unhandled packet!");
   		   }	     	    
	}

   public static String getString(DataInputStream datainputstream, int length, int max) throws IOException {
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

}
