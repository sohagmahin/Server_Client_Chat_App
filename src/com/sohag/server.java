package com.sohag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server extends JFrame{
    JTextField textField;
    JTextArea textArea;
    JButton button;
    ScrollPane scrollPane;
    Font font = new Font("Arial",Font.BOLD,18);
    Font txfont = new Font("Arial",Font.BOLD,14);
    Container c;
    public server(){
        initcontent();
    }

    private void initcontent() {
        setBounds(200,200,400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.MAGENTA);
        //text Area


        scrollPane = new ScrollPane();
        textArea= new JTextArea();
        textArea.setFont(txfont);
        textArea.setForeground(Color.GRAY);
        scrollPane.add(textArea);
        scrollPane.setBounds(20,10,350,250);
        c.add(scrollPane);

        textField = new JTextField("Write something!");
        textField.setBounds(20,270,250,60);
        textField.setFont(txfont);
        c.add(textField);

        button = new JButton("Send");
        button.setBounds(290,270,80,60);
        button.setFont(font);
        c.add(button);


    }

    public static void main(String[] args) throws Exception{
        server myserver = new server();
        myserver.setVisible(true);
        myserver.setTitle("Server");
        ServerSocket sSoc=new ServerSocket(1322);
        JOptionPane.showMessageDialog(null,"Server Start","Message", JOptionPane.INFORMATION_MESSAGE);
        Socket soc =sSoc.accept();
        Scanner sc = new Scanner(soc.getInputStream());
        while (true)
        {
            ///Receive Message.
            String receiveClientMessage = sc.nextLine();
            myserver.textArea.setText(myserver.textArea.getText().trim()+"\n"+receiveClientMessage);


            ///Sent Message.
            System.out.print("Server: ");
            myserver.button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = myserver.textField.getText();
                    myserver.textField.setText(null);
                    PrintStream out = null;
                    try {
                        out = new PrintStream(soc.getOutputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    out.println(str);
                }
            });

        }
    }
}
