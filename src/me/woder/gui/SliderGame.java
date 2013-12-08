package me.woder.gui;

/**
 * @since November 19, 2013
 * @author Bohan Jiang & co
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.color.*;
import java.util.Random;

public class SliderGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    Font font1 = new Font("georgia", Font.BOLD, 35);
    int[] derp = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    int loc = 0;
    private JButton[] buttons = new JButton[16];

    public SliderGame() {
        setSize(600, 600);
        setTitle("Slider Game");
        setForeground(Color.BLACK);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        shuffleArray(derp);

        int j = 0;

        for (int i = 0; i < buttons.length; i++) {

            int k = ((i % 4) * 100) + 100;

            if (i == 4 || i == 8 || i == 12) {
                j += 100;
            }
            buttons[i] = new JButton(i + "");
            buttons[i].setBounds(k, (100 + j), 100, 100);

            buttons[i].setText(String.valueOf(uniqueRandom()));

            buttons[i].setFont(font1);

            if (i == 0 || i == 2 || i == 5 || i == 7 || i == 8 || i == 10
                    || i == 13 || i == 15) {
                buttons[i].setBackground(Color.ORANGE);
            }

            if (buttons[i] == buttons[15]) {
                buttons[i].setText("");
                buttons[i].setBackground(Color.BLACK);
            }

            buttons[i].addActionListener(this);

            add(buttons[i]);
            setVisible(true);
        }

    }
    
    public static void main(String[] args){     
        new SliderGame();
    }

    private int uniqueRandom() {
        int value = derp[loc];
        loc++;
        return value;        
    }
    
    private void shuffleArray(int[] array){
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {

            System.out.println("0");

        }

    }
}