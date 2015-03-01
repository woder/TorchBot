package me.woder.bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.network.Packet;
import me.woder.plugin.Plugin;
import me.woder.world.Block;
import me.woder.world.Location;

public class CommandHandler {
    Client c;
    public ImportCommand impor = new ImportCommand();
    private File f = new File("Permissions.txt");
    private Map<String,List<String>> commandPerms;
    private Map<String, String> userPerms;
    
    public CommandHandler(Client c) throws FileNotFoundException{
    	if (!f.exists()) {
      	  System.out.println("Working Directory = " + System.getProperty("user.dir"));
           throw new FileNotFoundException("Permissions.txt missing from" + System.getProperty("user.dir"));
        } 
        this.c = c;
        Scanner s = new Scanner(f);
        commandPerms = new TreeMap<String, List<String>>();
        userPerms = new TreeMap<String, String>();
        while(s.hasNextLine()) {
        	commandPerms.put(s.nextLine(), Arrays.asList(s.nextLine().split(" ")));
        }
        s.close();
    }
    
    public void processCommand(String command, String[] args, String username){
        if (!hasPermisssion(command, username)) {
        	//c.chat.sendMessage(username+" does not have permission for "+command);
        }else if(command.equalsIgnoreCase("help")){
            commandHelp(args, username); 
        }else if(command.equalsIgnoreCase("move")){
            if(args.length > 1){
             Player p = c.en.findPlayer(ChatColor.stripColor(args[1]));
             if(p != null){
               Location l = new Location(c.whandle.getWorld(),c.location.getX(), c.location.getY()-1, c.location.getZ());
               c.chat.sendMessage("Loc: " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ());
               Location loc = new Location(c.whandle.getWorld(), p.getLocation().getX(), p.getLocation().getY()-1, p.getLocation().getZ());
               c.move.runPathing(l, loc, 100);
             }else{
               c.chat.sendMessage(username + ": couldn't find a player by that name");
             }
            }else{
               c.chat.sendMessage("Wrong amount of arguments provided!");
            }
        }else if(command.equalsIgnoreCase("whereareyou")){
        	c.chat.sendMessage("I am at: " + c.location.getX() + ", " + c.location.getY() + ", " + c.location.getZ());
        }else if(command.equalsIgnoreCase("whatis")){
        	if(args.length > 3){
        		Block b = c.whandle.getWorld().getBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        	    if (b != null) {
        	        c.chat.sendMessage("Block is: " + b.getTypeId() + " and its meta data is: " + b.getMetaData());
        	    } else {
        	        c.chat.sendMessage("Failed :(");
        	    } 
        	}
        }else if(command.equalsIgnoreCase("holding")){
          if(args.length > 1){
            if(!ChatColor.stripColor(args[1]).equalsIgnoreCase(c.username)){
               Player p = c.en.findPlayer(ChatColor.stripColor(args[1]));
               c.chat.sendMessage("Holding: " + p.getHeldItem());
            }else{
               c.chat.sendMessage("Can't use this command on self!");
            }
          }else{
              c.chat.sendMessage("Wrong amount of arguments provided!");
          }
        }else if(command.equalsIgnoreCase("export")){
            impor.processCommand(c, args);
        }else if(command.equalsIgnoreCase("import")){
            c.whandle.getWorld().importer.importb(c, args[1]);
        }else if(command.equalsIgnoreCase("list")){
            for(Slot s : c.invhandle.inventory){
                c.chat.sendMessage("Slot " + s.slotnum + " cotains: " + s.getCount() + " of item " + s.getId());
            }
        }else if(command.equalsIgnoreCase("inv")){
           if(args.length > 2){
              c.invhandle.creativeSetSlot(Short.parseShort(args[1]), new Slot(Short.parseShort(args[1]), Short.parseShort(args[2]),(byte)1,(short)0,(byte)0));
           }else{
              c.chat.sendMessage("Wrong amount of arguments provided!"); 
           }
        }else if(command.equalsIgnoreCase("select")){    
            c.invhandle.selectSlot(Short.parseShort(args[1]));
        }else if(command.equalsIgnoreCase("place")){
            //The minus one on the Y axis is to correct for the fact that you place a block on a given face, so to place at y=2 you would actually send y=1
            Block b = c.whandle.getWorld().getBlock(c.location).getRelative(Integer.parseInt(args[1]), (Integer.parseInt(args[2])-1), Integer.parseInt(args[3]));
            c.whandle.getWorld().placeBlock(b.getX(), b.getY(), b.getZ(), 3);
        }else if(command.equalsIgnoreCase("dig")){
            Block b = c.whandle.getWorld().getBlock(c.location).getRelative(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            c.whandle.getWorld().digBlock(b.getX(), b.getY(), b.getZ(), (byte)1);
        }else if(command.equalsIgnoreCase("respawn")){
            try {
                ByteArrayDataOutput buf = ByteStreams.newDataOutput();
                Packet.writeVarInt(buf, 22);
                buf.writeByte(0);
                c.net.sendPacket(buf, c.out);
                c.chat.sendMessage("Respawned!");
            } catch (IOException e) {
                e.printStackTrace();
            }    
        }else if(command.equalsIgnoreCase("friends")){
            if(args.length > 2){
                if(args[1].equalsIgnoreCase("add")){
                 if(args[2].equalsIgnoreCase("*")){
                 }else{
                  if(!c.friends.contains(args[2])){
                      c.friends.add(args[2]);
                      c.chat.sendMessage("Added " + ChatColor.stripColor(args[2]) + " to the friendlist");
                  }else{
                      c.chat.sendMessage("Could not add because " + ChatColor.stripColor(args[2]) + " is already on the list");
                  }
                 }
                }else if(args[1].equalsIgnoreCase("delete")){
                  if(c.friends.contains(args[2])){
                      c.friends.remove(args[2]);
                  }  
                }
            }
        }else if(command.equalsIgnoreCase("respawn")){
        	try {
                ByteArrayDataOutput buf = ByteStreams.newDataOutput();
                Packet.writeVarInt(buf, 22);
                buf.writeByte(0);
                c.net.sendPacket(buf, c.out);
                c.chat.sendMessage("Respawned! YOU DERP FACE!!!!!!");
            } catch (Exception e) {
                c.gui.addText("Error respawning: " + e.getMessage());
            }
        } else if (command.equalsIgnoreCase("setuserperms")) {
        	setUserPerms(args[1],args[2]);
        } else if (command.equalsIgnoreCase("removeuserperms")) {
        	removeUserPerms(args[1]);
        } else{
            c.ehandle.handleCommand(command, args, username);
        }
    }
    
    //Console commands will assign a username of "self" to the command.
    public void processConsoleCommand(String message){
    	if(message.contains(c.prefix)){
            String commande = message.substring(message.indexOf(c.prefix));
            int d = commande.length();
            if(commande.indexOf(" ") != -1){
              d = commande.indexOf(" ");
            }
            String command = commande.substring(0, d);
            commande.trim();
            this.processCommand(command.replace(c.prefix, ""), commande.substring(d).split(" "), "self");
        }   else{
            c.chat.sendMessage(message);         
        }
    }
    
    public void commandHelp(String[] messages, String sender){
      if(messages.length > 1){
            List<String> helpl = new ArrayList<String>();
            String append = "";
            if(messages[1].equalsIgnoreCase("core")){
                c.chat.sendMessage("Hehehehehehe");
            }else{               
                pluginH(messages, append, helpl);
            }          
      }else{
        c.chat.sendMessage("Command use: help <plugin name> **Note that \"core\" contains all core bot commands**");
      }
    }
    
    public boolean hasPermisssion(String command, String username) {
    	if (username.equals("self")) {
    		return true;
    	}
    	String permLevel = userPerms.get(username);
    	List<String> commands = null;
    	if (permLevel!=null) {
    		 commands = commandPerms.get(permLevel);
    	}
    	boolean hasCommand = false;
    	if (commands!=null) {
    		hasCommand = commands.contains(command);
    	}
    	return hasCommand;
    }
    
    public void setUserPerms(String username, String permsLevel) {
    	userPerms.put(username, permsLevel);
    }
    public void removeUserPerms(String username) {
    	userPerms.remove(username);
    }
      
    public void pluginH(String[] messages, String append, List<String> helpl){
        for(Plugin p : c.ploader.plugins){
            if(p.name.equalsIgnoreCase(ChatColor.stripColor(messages[1]))){
               for(int z = 0; z < p.commands.size(); z++){
                   if(z == 0){
                       append = p.commands.get(z) + " - " + p.description.get(z);
                   }else{
                      String temp = ", " + p.commands.get(z) + " - " + p.description.get(z);
                      if((append.length()+temp.length())<100){
                        append += temp;
                      }else{
                        helpl.add(append);
                        append = p.commands.get(z) + " - " + p.description.get(z);                          
                      }
                   }
               }
               helpl.add(append);
               append = "";
            }
        }
        c.chat.sendMessage("=====Help: " + ChatColor.stripColor(messages[1]) + "=====");
        new DelayedMessageSender(c).delayedMessageSender(helpl, 1000, 1000); //The delay is there because a lot of servers have anti spam and even this delay isn't enough, increase as needed TODO: make configurab
    }

}
