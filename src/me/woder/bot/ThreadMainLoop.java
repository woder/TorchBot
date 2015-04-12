package me.woder.bot;

public class ThreadMainLoop implements Runnable {
    Client c;
    String server;
    String port;
    
    public ThreadMainLoop(Client c, String server, String port){
        this.c = c;
        this.server = server;
        this.port = port;
    }
    
    public ThreadMainLoop(Client c){
        this.c = c;
        this.server = c.servername;
        this.port = ""+c.port;
    }
    
    @Override
    public void run(){
        c.startBot(server, port);
    }

}
