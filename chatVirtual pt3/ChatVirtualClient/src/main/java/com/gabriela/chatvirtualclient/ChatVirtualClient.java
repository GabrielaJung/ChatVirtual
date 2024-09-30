/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gabriela.chatvirtualclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author gabriela
 */
public class ChatVirtualClient {

    // definindo informações iniciais 
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8084;
    private static Socket socket;
    private static PrintWriter out;

    public static void main(String[] args) {
        // criando chat com JFrame
        JFrame frame = new JFrame("Chat App");
        
        // definindo função ao encerrar conexão
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            // Ação quando a janela for fechada      
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    // encerrando conexão com socket
                    socket.close();
                } catch (IOException ex) {
                    // ignore
                }
                frame.dispose();
            }
        });

        // layout do JFrame
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JTextField textField = new JTextField();
        JButton sendButton = new JButton("Enviar");
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        frame.add(panel, BorderLayout.SOUTH);

        // definindo ação de enviar mensagem
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField.getText());
                textField.setText("");
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        frame.setVisible(true);

        try {
            // tentando estabelecer conexão com o servidor na porta 8084
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            
            // definindo saída e entrada
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                // adiciona a mensagem escrita no chat quando a mensagem não for nula
                messageArea.append(message + "\n");
            }
        } catch (IOException e) {
            // ignora
        }
    }

    // definindo ação de enviar mensagem
    private static void sendMessage(String message) {
        // envia a mensagem pela saída se ela for válida
        if (message != null && !message.trim().isEmpty()) {
            out.println(message);
        }
    }
}
