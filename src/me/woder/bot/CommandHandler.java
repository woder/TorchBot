package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.network.Packet;
import me.woder.plugin.Plugin;
import me.woder.world.Block;
import me.woder.world.Location;

public class CommandHandler {
    Client c;
    public ImportCommand impor = new ImportCommand();
    
    public CommandHandler(Client c){
        this.c = c;
    }
    
    public void processCommand(String command, String[] args, String username){
        if(command.equalsIgnoreCase("help")){
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
            //c.whandle.getWorld().placeBlock(c.location.getBlockX()+2, c.location.getBlockY()-1, c.location.getBlockZ(), 3);
            c.whandle.getWorld().placeBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), 3);
        }else if(command.equalsIgnoreCase("dig")){
            c.whandle.getWorld().digBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), (byte)1);
        }else if(command.equalsIgnoreCase("friends")){
            if(args.length > 2){
                if(args[1].equalsIgnoreCase("add")){
                  if(!c.friends.contains(args[2])){
                      c.friends.add(args[2]);
                      c.chat.sendMessage("Added " + ChatColor.stripColor(args[2]) + " to the friendlist");
                  }else{
                      c.chat.sendMessage("Could not add because " + ChatColor.stripColor(args[2]) + " is already on the list");
                  }
                }else if(args[1].equalsIgnoreCase("delete")){
                  if(c.friends.contains(args[2])){
                      c.friends.remove(args[2]);
                  }  
                }
            }
        }else{
            c.ehandle.handleCommand(command, args, username);
        }
    }
    
    public void processConsoleCommand(String message){
        if(message.startsWith("-")){
            String[] s = message.split(" ");
            if(s[0].equalsIgnoreCase("isend")){
               c.irc.sendMessage(s[1], s[2]);
               c.gui.addText(message);
            }
        }else{
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
        try {
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
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
