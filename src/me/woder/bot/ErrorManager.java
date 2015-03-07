package me.woder.bot;

public class ErrorManager {
    Client c;
    
    public ErrorManager(Client c){
        this.c = c;
    }
    
    public void displayError(String error, String errorType, String cause){
        c.gui.addText(errorType + ": " + error);
    }

}
