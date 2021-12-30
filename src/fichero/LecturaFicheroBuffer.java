/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichero;
import palabra.Palabra;
import palabra.Rubrica;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class LecturaFicheroBuffer {
    private FileReader f;
    private BufferedReader br;
    private int c; // letra para ir leyendo
    
    public LecturaFicheroBuffer(String nom) throws Exception {
        f = new FileReader(nom);
        br = new BufferedReader(f);
    }
    
    public void cerrar() throws Exception {
        br.close();
        f.close();
    }
    
    public String leerTodoLineaALinea() throws Exception{
        String s ="";
        String l = br.readLine();
        while (l != null){
            s = s + l + '\n';
            l = br.readLine();
        }
        return s;
    }
    
    public Palabra leerPalabra() throws Exception{
        Palabra aux = new Palabra(); //  la que se va a devovler
        //coloco sobre el primer char
        c = br.read();
        //mientras no estoy sobre el primer char de la palabra o final
        saltarBlancosYOtros();
           // mientras no final y char de palabra
           while(c != -1 && c >= 32){   
            //inserta letra en palabra
            aux.anadirCaracter((char)c);
            //lee siguiente char
            c = br.read();
            }
        
        return aux;
    }
    
    private void saltarBlancosYOtros() throws Exception{
         // mientras no final de fichero y c <= 32 (espacio o cualquier otro char)
         while(c!=-1 && c<=32){
             c = br.read();
         }
    }
    public static Palabra[] leerDiccionario(String nombre, int cantidadPalabrasFichero) throws Exception{
        Palabra[] palabras = new Palabra[cantidadPalabrasFichero];
        LecturaFicheroBuffer f = new LecturaFicheroBuffer(nombre);
        Palabra p = f.leerPalabra();
        int i = 0;
        while(!p.vacia()){
            palabras[i] = p;
            i++;
            p = f.leerPalabra();
        }
        f.cerrar();
        return palabras;
    }
    
    
    
    
}
