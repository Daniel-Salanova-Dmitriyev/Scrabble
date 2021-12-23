/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabra;
import fichero.*;
/**
 *
 * @author CASA
 */
public class Rubrica {
    private char letra;
    private int cantidad;
    private int puntos;
    public static int numeroLetras = 0;
    public static Rubrica[] rubricaPuntos;
    public Rubrica (){
       
    }
    public Rubrica (char l, int c, int p){
        this.letra = l;
        this.cantidad = c;
        this.puntos = p;
    }
    
    public Rubrica[] getRubricaPuntos(){
        return this.rubricaPuntos;
    }
    
    public char getLetra () {
        return this.letra;
    }
    public int getPuntos () {
        return this.puntos;
    }
    public static void generarRúbrica() throws Exception{ //Generamos la rubrica
        LecturaFicheroBuffer f = new LecturaFicheroBuffer("src\\\\fichero\\\\esp.alf");
        Palabra pal = f.leerPalabra();
        numeroLetras = pal.convertir();
        rubricaPuntos = new Rubrica[26];
        int i = 0; // Indice
        if(numeroLetras > 0){
            Palabra v = f.leerPalabra(); // vocales
            Palabra c = f.leerPalabra(); //cantidad
            Palabra p = f.leerPalabra(); // Puntos
            while(!v.vacia() && !c.vacia()){
                int cantidad = c.convertir();
                int puntos = p.convertir();
                Rubrica r = new Rubrica(v.getPal()[0],cantidad,puntos);
                rubricaPuntos[i] = r;
                i++;
                v = f.leerPalabra(); // vocales
                c = f.leerPalabra(); //cantidad
                p = f.leerPalabra(); // Puntos
            }
        }
        
        f.cerrar(); // Cerramos el buffer
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        String str = "{'letra':" + this.letra + " ,'cantidad':" + this.cantidad +", 'puntos':" + this.puntos + "}";
        return str;
    }
    
    
}
