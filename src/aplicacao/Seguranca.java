/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacao;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author robson
 */
public class Seguranca {
    
    // Criptografa a senha antes de salvar
    public static String hashSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Verifica se a senha digitada bate com o hash salvo no banco
    public static boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return BCrypt.checkpw(senhaDigitada, senhaArmazenada);
    }
}
