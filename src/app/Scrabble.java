/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import fichero.LecturaFicheroBuffer;
import fichero.*;
import palabra.*;
import game.*;

/**
 *
 * @author Daniel Salanova Dmitriyev
 */
public class Scrabble {
    public void app() throws Exception{
        Game nuevoJuego = new Game(); //Creamos el juego 
        nuevoJuego.menu(); //Iniciamos el menu    
    }
    
    public static void main(String[] args)throws Exception {
        Scrabble a = new Scrabble();
        a.app();
    }
    
}
