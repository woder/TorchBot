package me.woder.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class Perms {
    Client c;
    private File f = new File("Permissions.txt");
    private File usr = new File("Users.txt");
    private Map<Integer,List<String>> commandPerms;
    private Map<String, Integer> userPerms;
    
    public Perms(Client client){
        this.c = client;
        try{
          if(!f.exists()) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            URL inputUrl = getClass().getResource("/PermissionsCopy.txt");
            FileUtils.copyURLToFile(inputUrl, f);
          }
          Scanner s = new Scanner(f);
          commandPerms = new TreeMap<Integer, List<String>>();
          userPerms = new TreeMap<String, Integer>();
          while(s.hasNextLine()) {
              commandPerms.put(Integer.parseInt(s.nextLine()), new ArrayList<String>(Arrays.asList(s.nextLine().split(" "))));
          }
          s.close();
          if(usr.exists()){
              Scanner sc = new Scanner(usr);
              while(sc.hasNextLine()){
                  String[] line = sc.nextLine().split(" ");
                  if(line.length > 1){
                     userPerms.put(line[0], Integer.parseInt(line[1]));
                  }else{
                     c.gui.addText(ChatColor.DARK_RED + "Error! Incorect file format found!");  
                  }
              }
              sc.close();
          }
        }catch(FileNotFoundException e){
            c.gui.addText("Warning: Permissions file could not be found");
        }catch(NumberFormatException e){
            c.gui.addText("Warning: Incorect file syntax (what should be a numer was unparsable)");
        } catch (IOException e) {
            c.gui.addText("Fatal IO error: " + e.getMessage());
        }
    }
    
    public boolean register(int level, String command){
        boolean result = false;
        List<String> commands = commandPerms.get(level);
        if(commands != null && !commands.contains(commands)){
            commands.add(command);
            result = true;
        }
        return result;
    }
    
    public boolean hasPermisssion(String command, String username) {
        if (username.equals("self")) {
            return true;
        }
        Integer permLevel = userPerms.get(username);
        List<String> commands = new ArrayList<String>();
        if(permLevel != null){
         for(int i = permLevel; i >= 0; i--){
         commands.addAll(commandPerms.get(i));
         }
        }
        boolean hasCommand = false;
        if (commands!=null) {
            hasCommand = commands.contains(command);
        }
        return hasCommand;
    }
    
    public void setUserPerms(String username, String permsLevel) {
       try{
        addLine(username + " " + permsLevel);
        userPerms.put(username, Integer.parseInt(permsLevel));
        c.chat.sendMessage("User " + username + " granted permission level " + permsLevel);
       }catch(NumberFormatException e){
           c.chat.sendMessage("Warning: " + permsLevel + " is not an Integer!");
       }
    }
    public void removeUserPerms(String username) {
        removeLine(username);
        userPerms.remove(username);
        c.chat.sendMessage("Revoked all permissions for " + username);
    }
    
    private void addLine(String line){
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(usr, true)))) {
            out.println(line + "\n");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void removeLine(String line){
        try {

          if (!usr.isFile()) {
            System.out.println("Parameter is not an existing file");
            return;
          }

          //Construct the new file that will later be renamed to the original filename.
          File tempFile = new File(usr.getAbsolutePath() + ".tmp");

          BufferedReader br = new BufferedReader(new FileReader(usr));
          PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

          String lin = null;

          //Read from the original file and write to the new
          //unless content matches data to be removed.
          while ((lin = br.readLine()) != null) {

            if(!isContain(lin.trim(),line)){ //if it contains the username and exactly the username
              pw.println(lin);
              pw.flush();
            }
          }
          pw.close();
          br.close();

          //Delete the original file
          usr.delete();

          //Rename the new file to the filename the original file had.
          if (!tempFile.renameTo(usr))
            c.chat.sendMessage("Error occured");

        }catch (IOException e) {
            e.printStackTrace();
        }
      }
    
    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
   }

}
