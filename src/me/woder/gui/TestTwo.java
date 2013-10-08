package me.woder.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestTwo {

    Double[] Props = new Double[6];

    public static void main(String[] args) {
       JFrame f = new JFrame("Balance Chart");
       //  f.setSize(500, 500);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       RComponent ccOne = new RComponent(50, 50, 5, 5, "bad lol", 0);
       RComponent ccTwo = new RComponent(0, 0, 5, 5, "lol bad", 1);
       JPanel container = new JPanel();
       container.setLayout(null);
       container.add(ccOne);
       container.add(ccTwo);
       f.add(container);
       f.pack();
       f.setLocationByPlatform(true);
       f.setVisible(true);
    }

 }