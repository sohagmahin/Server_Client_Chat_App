package com.sohag;


import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class client extends JFrame {
    JTextField textField;
    JTextArea textArea;
    JButton button;
    ScrollPane scrollPane;
    Font font = new Font("Arial",Font.BOLD,18);
    Font txfont = new Font("Arial",Font.BOLD,14);
    Container c;

    public client() {
        initcontent();
    }

    private void initcontent() {
        setBounds(500, 200, 400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.GREEN);
        //text Area


        scrollPane = new ScrollPane();
        textArea = new JTextArea();
        textArea.setForeground(Color.GRAY);
        textArea.setFont(txfont);
        scrollPane.add(textArea);
        scrollPane.setBounds(20, 10, 350, 250);
        c.add(scrollPane);

        textField = new JTextField("Write something!");
        textField.setBounds(20, 270, 250, 60);
        textField.setFont(txfont);
        c.add(textField);

        button = new JButton("Send");
        button.setBounds(290, 270, 80, 60);
        button.setFont(font);
        c.add(button);


    }

    public static void main(String[] args) throws Exception {
        client myclient = new client();
        myclient.setVisible(true);
        myclient.setTitle("Client");
        Socket soc=null;
        try {
             soc = new Socket("127.0.0.1", 1322);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Server Not Running","ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,"Connected","Message", JOptionPane.INFORMATION_MESSAGE);


        while (true) {

            ///Sent Message.

            Socket finalSoc = soc;
            myclient.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = myclient.textField.getText();
                    myclient.textField.setText(null);
                    PrintStream out = null;
                    try {
                        out = new PrintStream(finalSoc.getOutputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    out.println(str);
                }
            });

            ///Receive Message.
            Scanner sc = new Scanner(soc.getInputStream());
            String receiveClientMessage = sc.nextLine();
            myclient.textArea.setText(myclient.textArea.getText().trim() +"\n"+ receiveClientMessage );

        }
    }
}

