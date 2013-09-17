package me.woder.bot;

import me.woder.gui.TorchGUI;

public class Main {
    static Client client;
    public static void main(String[] args){
       TorchGUI window;
       client = new Client();
       window = new TorchGUI(client);
       window.frame.setVisible(true);
       client.main(window);
    }

}
