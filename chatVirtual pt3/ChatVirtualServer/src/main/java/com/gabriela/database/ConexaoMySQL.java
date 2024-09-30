package com.gabriela.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gabri
 */
public class ConexaoMySQL {

    public static String status = "Não conectou...";

    public void consultar() {
        Statement s = null;
        Connection connection = ConexaoMySQL.getConexaoMySQL();

        try {
            s = (Statement) connection.createStatement();
            ResultSet r = null;
            r = s.executeQuery("Select * from usuarios");

            System.out.println("ID       NOME");
            
            while (r.next()) {
                System.out.println(r.getString("nome") + "  " + r.getString("senha"));
            }

            r.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método Construtor da Classe//
    public ConexaoMySQL() {
    }

    public static java.sql.Connection getConexaoMySQL() {
        // Atributo do tipo Connection
        Connection connection = null;

        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Endereço do servidor do BD
        String serverName = "localhost";

        // Nome do banco de dados
        String mydatabase = "ChatVirtual3";

        // String de Conexão.
        String url = "jdbc:mysql://" + serverName + ":3306/" + mydatabase;

        // Nome de usuário do BD
        String username = "root";

        // A senha de acesso do usuário informado acima.
        String password = "#gabiDB1610!";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        // Testa a conexão
        try (Connection conn = getConexaoMySQL()) {
            System.out.println("Conectado ao banco de dados com sucesso! " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
