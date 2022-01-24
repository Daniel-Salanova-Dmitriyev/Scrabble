/**
 *
 * @author Daniel Salanova Dmitriyev Grupo 2
 */

package app;

import fichero.LecturaFichero;
import fichero.*;
import palabra.*;
import game.*;



public class Scrabble {
    public void app() throws Exception{
        Game nuevoJuego = new Game(8); //Creamos el juego y indicamos cuantos segundos queremos para pensar
        nuevoJuego.menu(); //Iniciamos el menu    
    }
    
    public static void main(String[] args)throws Exception {
        Scrabble a = new Scrabble();
        a.app();
    }
    
}
