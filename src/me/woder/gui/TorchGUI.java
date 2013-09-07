package me.woder.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;

import javax.swing.JFrame;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import me.woder.bot.ChatColor;
import me.woder.bot.Client;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TorchGUI extends JPanel{
    private static final long serialVersionUID = 1L;
    public JFrame frame;
    private JTextField textField;
    Client c;
    JTextPane chat;
    JTextArea status;
    JLabel news;
    String text = "i have written source code for scrolling text messages from "+  
            "right to left using applets,but the requirement is that the text should be "+  
            "scrolled from bottom of the page and it has start from corner of window "+  
            "applet.Please,can anyone suggest me on how to do this. ";  
    
    /*private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("§")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("§")) {
                break;
            }
            index++;
        }
        return index;
    }*/
    
    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet black = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,0,0));
    final AttributeSet blue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,0,170));
    final AttributeSet green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,170,0));
    final AttributeSet dark_aqua = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,170,170));
    final AttributeSet dark_red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,0,0));
    final AttributeSet purple = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,0,170));
    final AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,170,0));
    final AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,170,170));
    final AttributeSet dark_grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,85,85));
    final AttributeSet indigo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,85,255));
    final AttributeSet bright_green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,255,85));
    final AttributeSet aqua = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,255,255));
    final AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,85,85));
    final AttributeSet pink = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,85,255));
    final AttributeSet yellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,255,85));
    final AttributeSet white = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,255,255));
    final AttributeSet reset = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.black);
    
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    DefaultStyledDocument doc = new DefaultStyledDocument() {
        private static final long serialVersionUID = 1L;
        public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
            //S = ChatColor.stripColor(str);            

            String text = getText(0, getLength());
            System.out.println("Text: " + text + " length + " + text.length());
            int before = offset;
            if (before < 0) before = 0;
            int after = text.length();
            int wordL = before;
            int wordR = before;

            while (wordR <= after) {
                if (wordR == after || String.valueOf(text.charAt(wordR)).matches("§")) {
                    if (text.substring(wordL, wordR).contains("§0")){
                        setCharacterAttributes(wordL, text.length(), black, false);
                    }else if(text.substring(wordL, wordR).contains("§1")){
                        setCharacterAttributes(wordL, text.length(), blue, false);
                    }else if(text.substring(wordL, wordR).contains("§2")){
                        setCharacterAttributes(wordL, text.length(), green, false);
                    }else if(text.substring(wordL, wordR).contains("§3")){
                        setCharacterAttributes(wordL, text.length(), dark_aqua, false);
                    }else if(text.substring(wordL, wordR).contains("§4")){
                        setCharacterAttributes(wordL, text.length(), dark_red, false);
                    }else if(text.substring(wordL, wordR).contains("§5")){
                        setCharacterAttributes(wordL, text.length(), purple, false);
                    }else if(text.substring(wordL, wordR).contains("§6")){
                        setCharacterAttributes(wordL, text.length(), orange, false);
                    }else if(text.substring(wordL, wordR).contains("§7")){
                        setCharacterAttributes(wordL, text.length(), grey, false);
                    }else if(text.substring(wordL, wordR).contains("§8")){
                        setCharacterAttributes(wordL, text.length(), dark_grey, false);
                    }else if(text.substring(wordL, wordR).contains("§9")){
                        setCharacterAttributes(wordL, text.length(), indigo, false);
                    }else if(text.substring(wordL, wordR).contains("§a")){
                        setCharacterAttributes(wordL, text.length(), bright_green, false);
                    }else if(text.substring(wordL, wordR).contains("§b")){
                        setCharacterAttributes(wordL, text.length(), aqua, false);
                    }else if(text.substring(wordL, wordR).contains("§c")){
                        setCharacterAttributes(wordL, text.length(), red, false);
                    }else if(text.substring(wordL, wordR).contains("§d")){
                        setCharacterAttributes(wordL, text.length(), pink, false);
                    }else if(text.substring(wordL, wordR).contains("§e")){
                        setCharacterAttributes(wordL, text.length(), yellow, false);
                    }else if(text.substring(wordL, wordR).contains("§f")){
                        setCharacterAttributes(wordL, text.length(), white, false);
                    }else if(text.substring(wordL, wordR).contains("§f")){
                        setCharacterAttributes(wordL, text.length(), reset, false);
                    }else{
                        setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                    }
                    wordL = wordR;
                    
                }
                wordR++;
            }
            super.insertString(offset, ChatColor.stripColor(str), a);
        }

        public void remove (int offs, int len) throws BadLocationException {
            super.remove(offs, len);

            String text = getText(0, getLength());
            int before = 0;
            if (before < 0) before = 0;
            int after = len;
            
            if (text.substring(before, after).contains("§0")){
                setCharacterAttributes(before, after - before, black, false);
            }else if(text.substring(before, after).contains("§1")){
                setCharacterAttributes(before, after - before, blue, false);
            }else if(text.substring(before, after).contains("§2")){
                setCharacterAttributes(before, after - before, green, false);
            }else if(text.substring(before, after).contains("§3")){
                setCharacterAttributes(before, after - before, dark_aqua, false);
            }else if(text.substring(before, after).contains("§4")){
                setCharacterAttributes(before, after - before, dark_red, false);
            }else if(text.substring(before, after).contains("§5")){
                setCharacterAttributes(before, after - before, purple, false);
            }else if(text.substring(before, after).contains("§6")){
                setCharacterAttributes(before, after - before, orange, false);
            }else if(text.substring(before, after).contains("§7")){
                setCharacterAttributes(before, after - before, grey, false);
            }else if(text.substring(before, after).contains("§8")){
                setCharacterAttributes(before, after - before, dark_grey, false);
            }else if(text.substring(before, after).contains("§9")){
                setCharacterAttributes(before, after - before, indigo, false);
            }else if(text.substring(before, after).contains("§a")){
                setCharacterAttributes(before, after - before, bright_green, false);
            }else if(text.substring(before, after).contains("§b")){
                setCharacterAttributes(before, after - before, aqua, false);
            }else if(text.substring(before, after).contains("§c")){
                setCharacterAttributes(before, after - before, red, false);
            }else if(text.substring(before, after).contains("§d")){
                setCharacterAttributes(before, after - before, pink, false);
            }else if(text.substring(before, after).contains("§e")){
                setCharacterAttributes(before, after - before, yellow, false);
            }else if(text.substring(before, after).contains("§f")){
                setCharacterAttributes(before, after - before, white, false);
            }else{
                setCharacterAttributes(before, after - before, attrBlack, false);
            }
        }
    };
    
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        // Assume x, y, and diameter are instance variables.
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, 2, 2);
        g2d.fill(circle); 
    }
    
    /** launch it up
     * 
     */
    

    /**
     * Create the application.
     */
    public TorchGUI(Client c) {
        this.c = c;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("TorchBot 2.1");
        frame.setBounds(100, 100, 944, 555);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 520, 454);
        frame.getContentPane().add(scrollPane);
        
        chat = new JTextPane(doc);
        scrollPane.setViewportView(chat);
        chat.setEditable(false);
        
        
        textField = new JTextField();
        textField.setBounds(10, 476, 447, 33);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        status = new JTextArea();
        status.setBounds(540, 250, 262, 215);
        frame.getContentPane().add(status);
        status.setEditable(false);
        
        JTextArea textArea_2 = new JTextArea();
        textArea_2.setBounds(540, 12, 262, 228);
        frame.getContentPane().add(textArea_2);
        
        JButton btnNewButton = new JButton("Send");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                c.chandle.processConsoleCommand(arg0.getActionCommand());
                textField.setText("");
                chat.setCaretPosition(chat.getText().length());
            }
        });
        btnNewButton.setBounds(464, 476, 66, 33);
        frame.getContentPane().add(btnNewButton);
        
        news = new JLabel(text, Label.RIGHT);
        news.setBounds(540, 476, 262, 33);
        frame.getContentPane().add(news);
        
        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.setBounds(812, 13, 106, 23);
        frame.getContentPane().add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Follow");
        btnNewButton_2.setBounds(812, 47, 106, 23);
        frame.getContentPane().add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Place holder");
        btnNewButton_3.setBounds(812, 115, 106, 23);
        frame.getContentPane().add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("Place holder");
        btnNewButton_4.setBounds(812, 149, 106, 23);
        frame.getContentPane().add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Place holder");
        btnNewButton_5.setBounds(812, 183, 106, 23);
        frame.getContentPane().add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("Place holder");
        btnNewButton_6.setBounds(812, 217, 106, 23);
        frame.getContentPane().add(btnNewButton_6);
        
        JButton btnNewButton_7 = new JButton("Place holder");
        btnNewButton_7.setBounds(812, 251, 106, 23);
        frame.getContentPane().add(btnNewButton_7);
        
        JButton btnNewButton_8 = new JButton("Place holder");
        btnNewButton_8.setBounds(812, 81, 106, 23);
        frame.getContentPane().add(btnNewButton_8);
        scrollTheText();
    }
    
    public void scrollTheText()  
    {  
      Timer tmr = new Timer();  
      tmr.scheduleAtFixedRate(new TimerTask()  
      {  
        public void run()  
        {  
          text = new StringBuffer(text.substring(1)).append(text.substring(0,1)).toString();  
          news.setText(text);  
        }  
      },500,100);  
    }  
    
    public void addText(String text){
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        try {
            doc.insertString(doc.getLength(), text + "\n", keyWord);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
