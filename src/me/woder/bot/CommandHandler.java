package me.woder.bot;

import java.util.Arrays;

public class CommandHandler {
    Client c;
    public CoreCommands core;
    public ImportCommand impor = new ImportCommand();
    
    public CommandHandler(Client c){
        this.c = c;
        this.core = new CoreCommands(c);
    }
    
    public void processCommand(String command, String[] args, String username){
        if(core.commands.containsKey(command) || Arrays.asList(c.cmds).contains(command)){
            if (!c.perms.hasPermisssion(command, username)) {
                c.chat.sendMessage("Sorry " + username + " you don't have permission to use that!");               
            }else{
                if(core.commands.containsKey(command)){
                    core.commands.get(command).runCommand(c, command, args, username);
                }else{
                    c.ehandle.handleCommand(command, args, username);
                }
            }
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

}
