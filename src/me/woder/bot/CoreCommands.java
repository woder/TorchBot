package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.json.UUIDResponse;
import me.woder.network.Packet;
import me.woder.plugin.Plugin;
import me.woder.world.Block;
import me.woder.world.Location;

interface Command {
    void runCommand(Client c, String command, String[] args, String username);
}

public class CoreCommands {
    Client c;
    HashMap<String, Command> commands = new HashMap<String, Command>();
    
    public CoreCommands(Client c){
       this.c = c; 
       
       commands.put("move", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               if(args.length > 1){
                   Player p = c.en.findPlayer(ChatColor.stripColor(args[1]));
                   if(p != null){
                     Location l = new Location(c.whandle.getWorld(),c.location.getX(), c.location.getY()-1, c.location.getZ());
                     System.out.println("Loc: " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ());
                     Location loc = new Location(c.whandle.getWorld(), p.getLocation().getX(), p.getLocation().getY()-1, p.getLocation().getZ());
                     c.move.runPathing(l, loc, 100);
                   }else{
                     //c.chat.sendMessage(username + ": couldn't find a player by that name");
                   }
                  }else{
                     //c.chat.sendMessage("Wrong amount of arguments provided!");
               } 
           };
       });
       
       commands.put("othername", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               if(args.length > 0){
                   c.chat.sendMessage("Looking up previous names for: " + ChatColor.stripColor(args[1]));
                   List<UUIDResponse> names = c.en.getNamesUUID(c.en.getUUIDName(ChatColor.stripColor(args[1])));
                   List<String> namli = new ArrayList<String>();
                   for(UUIDResponse s : names){
                      if(s.getChangedTo() != null){
                          Date time = new Date(Long.parseLong(s.getChangedTo()));
                          namli.add(s.getName() + " changed at " + time.toString());
                      }else{
                          namli.add(s.getName());
                      }
                   }
                   new DelayedMessageSender(c).delayedMessageSender(namli, 0, 1000);
               } 
           };
       });
       
       commands.put("whereareyou", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.chat.sendMessage("I am at: " + c.location.getX() + ", " + c.location.getY() + ", " + c.location.getZ());
           };
       });
       
       commands.put("getblock", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.whandle.getWorld().getBlock(c.location.getBlockX(),c.location.getBlockY()-1,c.location.getBlockZ());
           };
       });
       
       commands.put("whatis", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               if(args.length > 3){
                   Block b = c.whandle.getWorld().getBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                   if (b != null) {
                       c.chat.sendMessage("Block is: " + b.getTypeId() + " and its meta data is: " + b.getMetaData());
                   } else {
                       c.chat.sendMessage("Failed :(");
                   } 
               }
           };
       });
       
       commands.put("holding", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
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
           };
       });
       
       commands.put("export", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               //impor.processCommand(c, args); TODO edit this
           };
       });
       
       commands.put("import", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.whandle.getWorld().importer.importb(c, args[1]);
           };
       });
       
       commands.put("list", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               for(Slot s : c.invhandle.inventory){
                   c.chat.sendMessage("Slot " + s.slotnum + " cotains: " + s.getCount() + " of item " + s.getId());
               }
           };
       });
       
       commands.put("inv", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
                if(args.length > 2){
                   c.invhandle.creativeSetSlot(Short.parseShort(args[1]), new Slot(Short.parseShort(args[1]), Short.parseShort(args[2]),(byte)1,(short)0,(byte)0));
                }else{
                   c.chat.sendMessage("Wrong amount of arguments provided!"); 
                }
           };
       });
       
       commands.put("select", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.invhandle.selectSlot(Short.parseShort(args[1]));
           };
       });
       
       commands.put("place", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               //The minus one on the Y axis is to correct for the fact that you place a block on a given face, so to place at y=2 you would actually send y=1
               Block b = c.whandle.getWorld().getBlock(c.location).getRelative(Integer.parseInt(args[1]), (Integer.parseInt(args[2])-1), Integer.parseInt(args[3]));
               c.whandle.getWorld().placeBlock(b.getX(), b.getY(), b.getZ(), 3);
           };
       });
       
       
       commands.put("dig", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               Block b = c.whandle.getWorld().getBlock(c.location).getRelative(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
               c.whandle.getWorld().digBlock(b.getX(), b.getY(), b.getZ(), (byte)1);
           };
       });
       
       
       commands.put("respawn", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               try {
                   ByteArrayDataOutput buf = ByteStreams.newDataOutput();
                   Packet.writeVarInt(buf, 22);
                   buf.writeByte(0);
                   c.net.sendPacket(buf, c.out);
                   //c.chat.sendMessage("Respawned!");
               } catch (IOException e) {
                   e.printStackTrace();
               }   
           };
       });
       
       commands.put("friends", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               if(args.length > 2){
                   if(args[1].equalsIgnoreCase("add")){
                    if(args[2].equalsIgnoreCase("*")){
                    }else{
                     if(!c.friends.contains(args[2])){
                         c.friends.add(args[2]);
                         System.out.println("Added " + ChatColor.stripColor(args[2]) + " to the friendlist");
                     }else{
                         System.out.println("Could not add because " + ChatColor.stripColor(args[2]) + " is already on the list");
                     }
                    }
                   }else if(args[1].equalsIgnoreCase("delete")){
                     if(c.friends.contains(args[2])){
                         c.friends.remove(args[2]);
                     }  
                   }
               }
           };
       });
       
       commands.put("setuserperms", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.perms.setUserPerms(ChatColor.stripColor(args[1]),args[2]);
           };
       });
       
       commands.put("removeuserperms", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.perms.removeUserPerms(ChatColor.stripColor(args[1]));
           };
       });
       
       /*commands.put("removeuserperms", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               c.invhandle.swapSlots(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
           };
       });*/
       
       commands.put("help", new Command() {
           @Override
        public void runCommand(Client c, String command, String[] args, String username) { 
               if(args.length > 1){
                   List<String> helpl = new ArrayList<String>();
                   String append = "";
                   if(args[1].equalsIgnoreCase("core")){
                       c.chat.sendMessage("Hehehehehehe");
                   }else{               
                       pluginH(args, append, helpl, c);
                   }          
             }else{
               c.chat.sendMessage("Command use: help <plugin name> **Note that \"core\" contains all core bot commands**");
             }
           };
           
           public void pluginH(String[] messages, String append, List<String> helpl, Client c){
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
       });
      
    }
}
