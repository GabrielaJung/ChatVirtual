/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gabriela.chatvirtualserver;

import java.io.*;
import java.net.*;
import java.util.*;
import com.gabriela.database.Services;

/**
 *
 * @author Gabriela
 */
public class ChatVirtualServer {

    // definindo informações iniciais
    private static final int PORT = 8084;
    private static final int MAX_CLIENTS = 10;

    // instanciando clientes
    private static final Set<PrintWriter> clientWriters = new HashSet<>();
    private static Map<String, PrintWriter> clientMap = new HashMap<>();  // Mapa de usuários

    Services services = new Services();

    public static void main(String[] args) {
        System.out.println("Iniciando servidor na porta " + PORT);

        // tentando iniciar servidor na porte 8084
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // aceitando conexão com o cliente
                Socket clientSocket = serverSocket.accept();

                // sincronizando informações
                synchronized (clientWriters) {
                    if (clientWriters.size() >= MAX_CLIENTS) {
                        // ações ao atingir o limite máximo de usuários:
                        System.out.println("Número máximo de clientes atingido. Rejeitando cliente...");

                        // feedback para o usuário 
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("Servidor cheio. Conexão recusada.");

                        // encerra conexão do usuário, impedindo ele de interagir e ler as mensagens
                        clientSocket.close();
                    } else {
                        // aceita e inicia conexão com usuário
                        new ClientHandler(clientSocket).start();
                    }
                }
            }
        } catch (IOException e) {
            // ignora
        }
    }

    private static class ClientHandler extends Thread {

        // definindo informações iniciais
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;
        private String password;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // definindo entrada e saída
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Solicita e recebe o nome de usuário
                out.println("Digite seu nome de usuário:");
                userName = in.readLine();

                // Solicita e recebe o senha do usuário
                out.println("Digite sua senha:");
                password = in.readLine();

                if (userName == null || userName.isEmpty() && password == null || password.isEmpty()) {
                    socket.close();
                    return;
                }

                boolean senhaValida = Services.validaSenha(userName, password);

                if (!senhaValida) {
                    out.println("Senha incorreta. Não foi possível conectar-se como " + userName + ".");
                    socket.close();
                    return;
                }
                
                out.println("Conectando...");

                // Adiciona o usuário ao mapa de clientes
                synchronized (clientWriters) {
                    clientWriters.add(out);
                    clientMap.put(userName, out);
                }

                // Informa todos os clientes que o novo usuário entrou
                broadcastMessage("Servidor: " + userName + " entrou no chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    broadcastMessage(userName + ": " + message);  // Envia a mensagem com o nome de usuário
                }
            } catch (IOException e) {
                // ignora
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // ignora
                }

                // Remove o usuário do mapa e lista de clientes conectados
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                    clientMap.remove(userName);
                }

                // Informa todos os clientes que o usuário saiu
                broadcastMessage("Servidor: " + userName + " saiu do chat.");
            }
        }

        private void broadcastMessage(String message) {
            System.out.println(message);  // Exibe a mensagem no console do servidor

            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
