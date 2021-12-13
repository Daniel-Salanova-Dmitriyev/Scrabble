/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import fichero.LecturaFicheroBuffer;
import fichero.*;
import palabra.*;

/**
 *
 * @author Daniel Salanova Dmitriyev
 */
public class Scrabble {
    public void app(){
        /*Palabra[] diccionario = LecturaFicheroBuffer.leer("src\\fichero\\esp.dic");
        LT l = new LT();
        String palabra = l.leerLinea();
        char[] palabraChar = palabra.toCharArray();
        Palabra p = new Palabra();
        for(int i = 0; i<palabraChar.length; i++){
            p.anadirCaracter(palabraChar[i]);
        }
        Palabra e = Palabra.encontrarPalabra(diccionario, p);
        System.out.println(e); */
        
        Rubrica[] rubrica = LecturaFicheroBuffer.alfabeto("src\\\\fichero\\\\esp.alf");
     
    }
    
    public static void main(String[] args) {
        Scrabble a = new Scrabble();
        a.app();
    }
    
}
