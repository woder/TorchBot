package me.woder.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import me.woder.bot.ChatColor;
import me.woder.bot.Client;


public class IRCBridge extends Thread{
    public BufferedReader reader;
    public InputStreamReader in;
    public BufferedWriter writer;
    public OutputStreamWriter out;
    public String server = "irc.synirc.net";
    public String nick = "d3bot";
    public String login = "nobody";
    public String pass = "";
    public String owner = "woder";
    public String realname = "woder is awesome";
    public String line;
    public String channel = "#mcblocks";
    public Socket sock;
    public int port = 6667;
    public Client c;
    
    
    public IRCBridge(Client c) {
        this.c = c;
    }
    @Override
    public void run(){
        try {
            connect();
            while(c.connectedirc){
                read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void connect() throws IOException {
        try {
            c.gui.addText("Connecting...");
            sock = new Socket(server, port);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            
            writer.write("USER " + nick + " idk.com " + server + " " + realname + "\n");
            writer.write("NICK " + nick + "\n");
            writer.flush();
            writer.write("JOIN " + channel + "\n");
            //writer.write("NS ID " + pass + "\n");
            writer.flush();
            c.gui.addText(ChatColor.AQUA + "Succesfully connected to IRC Server: " + server + ".\n");
            c.connectedirc = true;
        } catch (IOException ioe) {
            c.connectedirc = false;
            c.gui.addText("[IOExc]: Error connecting to the server.");
        }
    }
    
    public void read(){
      try{
            line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            String senderInfo = tokenizer.nextToken();
            String command2 = tokenizer.nextToken();
            int exclamation = senderInfo.indexOf("!");
            int at = senderInfo.indexOf("@");
            String sourceNick = "";
            if (exclamation > 0 && at > 0 && exclamation < at) {
                sourceNick = senderInfo.substring(1, exclamation);
                senderInfo.substring(exclamation + 1, at);
                senderInfo.substring(at + 1);
            }
            if (sourceNick.startsWith(":")) {
                sourceNick = sourceNick.substring(1);
            }
            if (line.split(" ").length >= 2 && line.split(" ")[0].equalsIgnoreCase("PING")) {
                writer.write("PONG " + line.split(" ")[1] + "\n");
                writer.flush();
                c.gui.addText("[PING? PONG!]");
            }
            else if (command2.equals("PRIVMSG") && line.split(" ")[2].startsWith("#")){
                if(line.split(" ")[2].equalsIgnoreCase(channel)){
                    String messages2 = line.substring(line.indexOf(" :") + 2);
                    String text = "*irc " + sourceNick + ": " + messages2;
                    c.gui.addText("Printed ingame: " + text);
                }
            }
            else if (command2.equals("PRIVMSG")){
                writer.write("PRIVMSG " + sourceNick + " Im a bridge between a server and irc, not a ircbot!\n");
                writer.flush();
            }        
      } catch (IOException ioe) {
            c.gui.addText(ChatColor.RED + "[IOExc]: Error connecting to the server.");
      }
    }
    
    public void sendMessage(String dst, String msg){
        try {
            writer.write("PRIVMSG " + dst + " " + msg + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
