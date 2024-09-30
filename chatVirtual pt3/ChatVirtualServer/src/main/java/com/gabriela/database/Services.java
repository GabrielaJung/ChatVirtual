package com.gabriela.database;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.gabriela.database.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gabri
 */
public class Services {

    // Método para buscar um usuário pelo nome

    /**
     *
     * @param nome
     * @param senhaDigitada
     * @return boolean
     */
    public static boolean validaSenha(String nome, String senhaDigitada) {
        String sql = "SELECT * FROM usuarios WHERE nome = ?";
        boolean result = false;

        try (Connection conn = ConexaoMySQL.getConexaoMySQL(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro
            stmt.setString(1, nome);

            // Executa o comando de consulta
            ResultSet rs = stmt.executeQuery();

            // Itera sobre os resultados da consulta
            while (rs.next()) {
                String senhaSalva = rs.getString("senha");
                result = senhaDigitada.equals(senhaSalva);
                // System.out.println("Senha: " + senha);
            }

            System.out.println("Senha é válida? " + result);

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        Services services = new Services();
//        services.validaSenha("Gabriela", "senha");
//        services.validaSenha("Gabriela", "senha123");
//        services.validaSenha("Pedro", "1q2w3e");
//        services.validaSenha("Carol", "1");
//
//    }
}
