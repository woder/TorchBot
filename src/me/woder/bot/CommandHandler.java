package me.woder.bot;

import java.io.IOException;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.network.Packet;
import me.woder.world.Block;
import me.woder.world.Location;

public class CommandHandler {
    Client c;
    public ImportCommand impor = new ImportCommand();
    
    public CommandHandler(Client c){
        this.c = c;
    }
    
    public void processCommand(String command, String[] args, String username){
        System.out.println("Command is: " + command);
        if(command.equalsIgnoreCase("help")){
            commandHelp(args, username); 
        }else if(command.equalsIgnoreCase("under")){
            Block b = c.whandle.getWorld().getBlock(c.location).getRelative(0, -1, 0);
            if (b != null) {
                c.chat.sendMessage("Block is: " + b.getTypeId() + " and its meta data is: " + b.getMetaData());
            } else {
                c.chat.sendMessage("Failed :(");
            }     
        }else if(command.equalsIgnoreCase("version")){
            c.chat.sendMessage(c.versioninfo);
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
        }else if(command.equalsIgnoreCase("move")){
            if(args.length > 1){
             Player p = c.en.findPlayer(ChatColor.stripColor(args[1]));
             if(p != null){
               Location l = new Location(c.whandle.getWorld(),c.location.getX(), c.location.getY()-1, c.location.getZ());
               c.chat.sendMessage("Loc: " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ());
               Location loc = new Location(c.whandle.getWorld(), p.getLocation().getX(), p.getLocation().getY()-1, p.getLocation().getZ());
               c.move.runPathing(l, loc, 50);
             }else{
               c.chat.sendMessage(username + ": couldn't find a player by that name");
             }
            }else{
               c.chat.sendMessage("Wrong amount of arguments provided!");
            }
        }else if(command.equalsIgnoreCase("holding")){
            Player p = c.en.findPlayer(ChatColor.stripColor(args[1]));
            c.chat.sendMessage("Holding: " + p.getHeldItem());
        }else if(command.equalsIgnoreCase("reload")){
            c.ploader.reloadPlugins();
        }else if(command.equalsIgnoreCase("export")){
            impor.processCommand(c, args);
        }else if(command.equalsIgnoreCase("import")){
            c.whandle.getWorld().importer.importb(c, args[1]);
        }else if(command.equalsIgnoreCase("list")){
            for(Slot s : c.invhandle.inventory){
                c.chat.sendMessage("Slot " + s.slotnum + " cotains: " + s.getCount() + " of item " + s.getId());
            }
        }else if(command.equalsIgnoreCase("inv")){
            c.invhandle.creativeSetSlot(Short.parseShort(args[1]), new Slot(Short.parseShort(args[1]), Short.parseShort(args[2]),(byte)1,(short)0,(short)-1));
        }else if(command.equalsIgnoreCase("select")){
            c.invhandle.selectSlot(Short.parseShort(args[1]));
        }else if(command.equalsIgnoreCase("place")){
            c.whandle.getWorld().placeBlock(c.location.getBlockX()+2, c.location.getBlockY()-1, c.location.getBlockZ(), 3);
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
        /*if (messages.length < 2){
            sendMessage(channel, "Catagorys are: op, fun, normal and plugin <Bot created by woder>"); 
        }else if (messages[1].equalsIgnoreCase("op")){
            sendMessage(channel, "Commands are: mod, unmod, kick, kickban, unban");
        }else if (messages[1].equalsIgnoreCase("normal")){
            sendMessage(channel, "Commands are: echo, time, isup, mcping, haspaid, calc(not implemented yet)");
        }else if (messages[1].equalsIgnoreCase("fun")){
            sendMessage(channel, "Commands are: slap, roulette, eightball"); */
      if(messages.length > 1){
        if (messages[1].equalsIgnoreCase("plugin")){
            String append = "";
            for(int z = 0; z < c.cmds.length; z++){
               if(z == 0){
                append = c.cmds[z] + " - " + c.descriptions[z];
               }else{
                append += ", " + c.cmds[z] + " - " + c.descriptions[z];
               }
            }
            c.chat.sendMessage(sender + ": " + append);
        }     
      }else{
        c.chat.sendMessage("Catagories are: op, fun, normal and plugin <Bot created by woder>");
      }
    }


}
