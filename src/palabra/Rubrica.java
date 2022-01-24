/**
 *
 * @author Daniel Salanova Dmitriyev Grupo 2
 */

package palabra;
import fichero.*;
/*
    Clase encargada de almacenar todos los datos del fichero esp.alf dentro de un array, cada objeto tendrá letra, cantidad y puntos
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
      
    public char getLetra () {
        return this.letra;
    }
    
    public int getPuntos () {
        return this.puntos;
    }
    
    public int getCantidad(){
        return this.cantidad;
    }
    
    public void setCantidad(int i){
        this.cantidad += i;
    }
    
    public static void generarRubrica() throws Exception{ //Generamos la rubrica
        LecturaFichero f = new LecturaFichero("src\\\\fichero\\\\esp.alf"); //Abrimos el fichero
        Palabra pal = f.leerPalabra(); // Leemos la primera linea que es a su vez la cantidad de letras
        numeroLetras = pal.convertir(); // Lo convertimos a número
        rubricaPuntos = new Rubrica[26]; // Siendo 26 el numero de letras que existen dentro de nuestro fichero alfabeto
        int i = 0; // Indice
        if(numeroLetras > 0){
            Palabra l = f.leerPalabra(); // letras
            Palabra c = f.leerPalabra(); //cantidad
            Palabra p = f.leerPalabra(); // Puntos
            while(!l.vacia() && !c.vacia() && !p.vacia()){ //Mientras existan los 3 elementos seguimos
                int cantidad = c.convertir(); // Como son números y lo que tenemos es un conjunto de char lo convertimos a int
                int puntos = p.convertir(); // Como son números y lo que tenemos es un conjunto de char lo convertimos a int
                Rubrica r = new Rubrica(l.getPal()[0],cantidad,puntos); // Generamos el objeto
                rubricaPuntos[i] = r; // Lo añadimos al array
                i++;
                l = f.leerPalabra(); // letras
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
