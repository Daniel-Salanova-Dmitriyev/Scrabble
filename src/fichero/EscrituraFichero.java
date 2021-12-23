/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichero;
import java.io.*;
import palabra.*;
/**
 *
 * @author alumno
 */
public class EscrituraFichero {
    private FileWriter f;
    private BufferedWriter bw;
    
    public EscrituraFichero(String nom) throws Exception {
        f = new FileWriter(nom, true); //Con true indicamos que se el archivo anterior para guardar partida
        bw = new BufferedWriter(f);
    }
    
    public void cerrar() throws Exception{
        bw.close();
        f.close();
    }
    
    public void escribirPalabra(Palabra p){
        
    }
    public void escribirString (String s) throws Exception{
        bw.newLine(); // Salto de linea
        bw.write(s);
    }
    
    /*public void escribirChar (char c) throws Exception{
        bw.write(c);
    }*/
}
