package me.woder.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Timer;

import me.woder.bot.Client;


public class IRCBridge {
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

	@SuppressWarnings("unused")
	public  void connect() throws IOException {
		try {
			System.out.println("Connecting...");
			sock = new Socket(server, port);
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			
			writer.write("USER " + nick + " idk.com " + server + " " + realname + "\n");
			writer.write("NICK " + nick + "\n");
			writer.flush();
			writer.write("JOIN " + channel + "\n");
			//writer.write("NS ID " + pass + "\n");
			writer.flush();
			System.out.println("Succesfully connected to IRC Server: " + server + ".\n");
			Timer timer = new Timer();

		} catch (IOException ioe) {
			System.out.println("[IOExc]: Error connecting to the server.");
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
				System.out.println("[PING? PONG!]");
			}
			else if (command2.equals("PRIVMSG") && line.split(" ")[2].startsWith("#")){
				if(line.split(" ")[2].equalsIgnoreCase(channel)){
					String messages2 = line.substring(line.indexOf(" :") + 2);
					String text = "*irc " + sourceNick + ": " + messages2;
					System.out.println("Printed ingame: " + text);
				}
			}
			else if (command2.equals("PRIVMSG")){
				writer.write("PRIVMSG " + sourceNick + " Im a bridge between a server and irc, not a ircbot!\n");
				writer.flush();
			}		
	  } catch (IOException ioe) {
			System.out.println("[IOExc]: Error connecting to the server.");
	  }
	}
}
