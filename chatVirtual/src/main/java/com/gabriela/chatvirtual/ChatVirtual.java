/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gabriela.chatvirtual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author gabriela
 *
 * public class ChatVirtual {;
 *
 * public static void main(String[] args) { System.out.println("Hello World!");
 * } }
 */
public class ChatVirtual {

    public static void main(String[] args) {
        // Criação da janela principal
        // janela básica para montagem da interface - utilizar um JFrame
        JFrame frame = new JFrame("Chat App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Configuração do layout
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Criação da área de texto para exibição das mensagens
        // caixa de texto de múltiplas linhas para exibição das mensagens - utilizar um JTextArea
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Criação do painel inferior com campo de texto e botão
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // caixa de texto de linha simples para escrita da mensagem a ser enviada - utilizar um JTextField
        JTextField textField = new JTextField();
        
        // botão para envio da mensagem - utilizar um JButton
        JButton sendButton = new JButton("Enviar");
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        // Configuração do botão de envio
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                if (!message.isEmpty()) {
                    messageArea.append("Você: " + message + "\n");
                    textField.setText("");
                }
            }
        });

        // Configuração da tecla Enter para enviar a mensagem
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        // Tornando a janela visível
        frame.setVisible(true);
    }
}
