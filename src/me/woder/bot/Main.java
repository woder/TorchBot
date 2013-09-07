package me.woder.bot;

import me.woder.gui.TorchGUI;

public class Main {
    static Client client;
    public static void main(String[] args){
       TorchGUI window;
       window = new TorchGUI(client);
       client = new Client(window);
       window.frame.setVisible(true);
       client.main();
    }

}
