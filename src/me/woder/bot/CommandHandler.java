package me.woder.bot;

import java.io.IOException;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import me.woder.world.Block;
import me.woder.world.Import;
import me.woder.world.Location;

public class CommandHandler {
    Client c;
    
    public CommandHandler(Client c){
        this.c = c;
    }
    
    public void processCommand(String message){
        //TODO fix this mess of a command handler
        if(message.contains(c.prefix)){
            String command = message.substring(message.indexOf(c.prefix));
            if(command.contains("echos")){
                String username = message.substring(0, message.indexOf(" "));
                username = username.replace("[<>:\\[\\]]", "");
                if(message.contains("/")){
                     if(username.contains("woder22")){
                         c.chat.sendMessage(command.replace("!echo", "")); 
                     }else{
                         c.chat.sendMessage("Only bot admins can make me use commands!");
                     }
                  }else{
                    c.chat.sendMessage(command.replace("!echo", ""));
                  }
            }else if(command.contains("respawn")){
                try {
                    c.out.writeByte(0xCD);        
                    c.out.writeByte(1);
                    c.out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            /*}else if(command.contains("hex")){
                int num = Integer.parseInt(command.replace("!hex", ""),16);;
                try {
                    c.chat.sendMessage("Hex: " + num);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else if(command.contains("dec")){
                String num = decimal2hex(Integer.parseInt(command.replace("!hex", "")));
                try {
                    c.chat.sendMessage("Dec: " + num);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }else if(command.contains("under")){
                //String[] args = command.split(" ");
                Block b = c.whandle.getWorld().getBlock(c.location).getRelative(0, -2, 0);
                if (b != null) {
                    c.chat.sendMessage("Block is: " + b.getTypeId() + " and its meta data is: " + b.getMetaData());
                } else {
                    c.chat.sendMessage("Failed :(");
                }
            }else if(command.contains("place")){
                try {
                    c.out.writeByte(0x0f);
                    c.out.writeInt(c.location.getBlockX()+1);
                    c.out.writeByte(c.location.getBlockY());
                    c.out.writeInt(c.location.getBlockZ());
                    c.out.writeByte(0);
                    c.out.writeShort(5);
                    c.out.writeShort(1);
                    c.out.writeShort(0);
                    c.out.writeShort(-1);
                    c.out.writeByte(8);
                    c.out.writeByte(8);
                    c.out.writeByte(8);
                    c.out.flush();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }else if(command.contains("version")){
                c.chat.sendMessage(c.versioninfo);
            }else if(command.contains("import")){
                Import im = new Import();
                im.importb(c, -470, 69, 485, -475, 72, 475);
            }else if(command.contains("move")){            
                Player p = c.findPlayer("woder22");
                Location loc = null;
                if(p != null){
                    c.chat.sendMessage("loc" + p.x + "," + (p.y-1) + "," + p.z);
                    loc = p.getLocationUnder();
                    c.chat.sendMessage("block is " + loc.getBlock().getTypeId());
                }
                //Location loc = new Location(c.whandle.getWorld(), 42, 63, 696);
                c.chat.sendMessage("loc" + c.location.getBlockX() + "," + (c.location.getBlockY()-2) + "," + c.location.getBlockZ());
                Location l = new Location(c.whandle.getWorld(),c.location.getBlockX(), c.location.getBlockY()-2, c.location.getBlockZ());          
                c.chat.sendMessage("block isd " + l.getBlock().getTypeId());
                c.move.runPathing(l, loc, 50);
            }else if(command.contains("server")){
                c.chat.sendMessage("/server hub");
                System.exit(0);
            }
        }
    }
    
    public void processConsoleCommand(String message){
        if(message.startsWith("/")){
            //TODO do stuff with commands
            AttributeSet attribute = c.gui.attributes.get(0);
            try {
                c.gui.doc.insertString(c.gui.doc.getLength(), message, attribute);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }else{
            c.chat.sendMessage(message);         
        }
    }


}
