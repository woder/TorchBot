package me.woder.bot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DelayedMessageSender {
    Client c;
    int delayed = 0;
    
    public DelayedMessageSender(Client c){
        this.c = c;
    }
    
    public void delayedMessageSender(final List<String> s, long delay, long period){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
             if(delayed < s.size()){
              c.chat.sendMessage(s, delayed);
              delayed++;
             }else{
              timer.cancel(); //Terminate the timer thread            
             }
          }
          }, delay, period);
    }

}
