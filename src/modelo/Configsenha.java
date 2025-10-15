/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author robson
 */
public class Configsenha {

    private boolean letramaiuscula;
    private boolean letraminuscula;
    private boolean caracterespecial;
    private boolean numeros;
    private boolean minimocaracter;

    public boolean isLetramaiuscula() {
        return letramaiuscula;
    }

    public void setLetramaiuscula(boolean letramaiuscula) {
        this.letramaiuscula = letramaiuscula;
    }
    
    public boolean isLetraminuscula() {
        return letraminuscula;
    }

    public void setLetraminuscula(boolean letraminuscula) {
        this.letraminuscula = letraminuscula;
    }

    public boolean isCaracterespecial() {
        return caracterespecial;
    }

    public void setCaracterespecial(boolean caracterespecial) {
        this.caracterespecial = caracterespecial;
    }

    public boolean isNumeros() {
        return numeros;
    }

    public void setNumeros(boolean numeros) {
        this.numeros = numeros;
    }

    public boolean isMinimocaracter() {
        return minimocaracter;
    }

    public void setMinimocaracter(boolean minimocaracter) {
        this.minimocaracter = minimocaracter;
    }

    @Override
    public String toString() {
        return "Configsenha{" + "letramaiuscula=" + letramaiuscula + ", letraminuscula=" + letraminuscula + ", caracterespecial=" + caracterespecial + ", numeros=" + numeros + ", minimocaracter=" + minimocaracter + '}';
    }
    
    
}
