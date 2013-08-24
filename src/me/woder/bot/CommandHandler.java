package me.woder.bot;

import java.io.IOException;

import me.woder.world.Block;
import me.woder.world.Location;

public class CommandHandler {
    Client c;
    
    public CommandHandler(Client c){
        this.c = c;
    }
    
    public void processCommand(String message){
        if(message.contains(c.prefix)){
            String command = message.substring(message.indexOf(c.prefix));
            if(command.contains("echo")){
                String username = message.substring(message.indexOf("&"), message.indexOf(":"));
                try{
                  if(message.contains("/")){
                     if(username.contains("woder22")){
                         c.chat.sendMessage(command.replace("!echo", "")); 
                     }else{
                         c.chat.sendMessage("Only bot admins can make me use commands!");
                     }
                  }else{
                    c.chat.sendMessage(command.replace("!echo", ""));
                  }
                } catch (IOException e) {
                    e.printStackTrace();
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
                try {
                    //String[] args = command.split(" ");
                    Block b = c.whandle.getWorld().getBlock(c.location).getRelative(0, -2, 0);
                    if (b != null) {
                        c.chat.sendMessage("Block is: " + b.getTypeId());
                    } else {
                        c.chat.sendMessage("Failed :(");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(command.contains("move")){
                Location loc = new Location(c.whandle.getWorld(), 47, 63, 671);
                Location l = new Location(c.whandle.getWorld(),c.location.getBlockX(), c.location.getBlockY(), c.location.getBlockZ()+1);            
                //c.move.runPathing(l, loc, 50);
                c.move.calcMovement(l);
            }else if(command.contains("server")){
                try {
                c.chat.sendMessage("/server hub");
                System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }


    // precondition:  d is a nonnegative integer
    public String decimal2hex(int d) {
        String digits = "0123456789ABCDEF";
        if (d == 0) return "0";
        String hex = "";
        while (d > 0) {
            int digit = d % 16;                // rightmost digit
            hex = digits.charAt(digit) + hex;  // string concatenation
            d = d / 16;
        }
        return hex;
    }

}
