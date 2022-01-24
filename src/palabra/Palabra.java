/**
 *
 * @author Daniel Salanova Dmitriyev Grupo 2
 */
package palabra;

import fichero.*;
import java.util.Random;

public class Palabra {

    private final int max = 20;
    private char[] pal;
    private int n;

    public Palabra() {
        pal = new char[max];
        n = 0;
    }

    public char[] getPal() {
        return this.pal;
    }

    public int getN() {
        return this.n;
    }

    public static void leerArray(Palabra[] a) {
        for (Palabra p : a) {
            System.out.println(p);
        }
    }

    public static Palabra encontrarPalabra(Palabra[] diccionario, Palabra palabra) { //Buscamos la palabra dentro del diccionario
        Palabra encontrada = new Palabra();
        for (Palabra p : diccionario) { //Recorremos el dicconario
            if (p.n == palabra.n && p.pal[0] == palabra.pal[0]) {
                if (sonIguales(palabra, p)) { // Si la hemos encontrado paramos el bucle
                    encontrada = p; 
                    break;
                }
            }
        }
        return encontrada; // devolvemos la palabra encontrada o una palabra vacia en caso de no haberla encontrado
    }

    public void anadirCaracter(char c) {
        pal[n++] = c;
    }

    public boolean vacia() {
        return (n == 0);
    }

    public static boolean sonIguales(Palabra p1, Palabra p2) { // Comparamos dos palabras
        boolean iguales = true;
        for (int i = 0; i < p1.n; i++) {
            if (p1.pal[i] != p2.pal[i]) {
                iguales = false;
                break;
            } else {
                iguales = true;
            }
        }
        return iguales;
    }

    public static int cantidadPalabrasEnDiccionario(String nombre) throws Exception { //Cuenta cuantas palabras hay dentro del diccionario
        int c = 0; //Contador a 0 inicialmente
        LecturaFichero f = new LecturaFichero(nombre);
        Palabra p = f.leerPalabra();
        while (!p.vacia()) { //Mientras no hayamos llegado al final
            c++; //Aumentamos la cantidad de palabras
            p = f.leerPalabra(); //Leemos la siguiente
        }
        f.cerrar(); //Liberamos recursos
        return c;
    }

    public int convertir() { // convierte a num
        int res = 0;
        int mult = 1;
        int n = this.n;
        for (int i = this.n - 1; i >= 0; i--) {
            res += (this.pal[i] - '0') * mult;
            mult *= 10;
        }
        return res;
    }

    public static Palabra pedirPalabra() {
        LT l = new LT();
        String palabra = l.leerLinea();
        char[] palabraChar = palabra.toCharArray();
        Palabra p = new Palabra();
        for (int j = 0; j < palabraChar.length; j++) {
            p.anadirCaracter(palabraChar[j]);
        }
        return p;
    }

    public static Palabra[] comprobarEnFichasPalabras(Palabra[] fichas, Palabra[] diccionario) { //Metodo para el bot, comprobará todas las palabras que pueden contener sus fichas
        Palabra[] palabrasEncontradas = new Palabra[700]; //Todas las palabras que se pueden hacer con las fichas
        boolean[] usadas = new boolean[fichas.length]; //Array booleano, inicialmente todos en false
        int contadorPalabra = 0;
        int similitud = 0; //Si es igual al tamaño de la palabra son iguales
        for (Palabra p : diccionario) { // Recorremos el diccionario entero
            for (int i = 0; i < p.getN(); i++) { // Recorremos cada letra de la palabra
                for (int j = 0; j < fichas.length && fichas[j] != null; j++) { //Buscamos letra dentro de nuestras fichas
                    if (fichas[j].getPal()[0] == p.getPal()[i] && usadas[j] == false) { //Si son iguales y no esta usada 
                        usadas[j] = true;
                        similitud++;
                        break;
                    }
                }

            }

            if (similitud == p.getN() && contadorPalabra + 1 < 700) { //Comprobamos que este entera la palabra en nuestras fichas
                palabrasEncontradas[contadorPalabra] = p;
                contadorPalabra++;
            }

            similitud = 0; //Volvemos a poner a 0 la similitud para la siguiente palabra
            usadas = new boolean[fichas.length]; // Volvemos a poner a false todas las posiciones del array
        }
        return palabrasEncontradas;
    }

    public boolean comprobarPalabraEnFichas(Palabra[] fichas) { //Metodo para usuario, comprobará que no consume fichas de más y que no se inventa letras
        int contador = 0; // Si el contador es igual a la longitud de la palabra es que se puede crear con las fichas que tenemos la palabra
        Palabra[] arrFichas = fichas;
        boolean[] usadas = new boolean[fichas.length]; // Array con todas las posiciones en false
        usadas = inicializarUsadas(usadas);
        for (int j = 0; j < this.n; j++) { // Recorremos todas las letras de la palabra
            for (int i = 0; i < arrFichas.length && arrFichas[i] != null; i++) { // != null, en el caso de que se cambie que podamos crear partidas con alguna ronda < 11 habrá posiciones nulas
                if (this.pal[j] == arrFichas[i].getPal()[0] && usadas[i] == false) {
                    usadas[i] = true;
                    contador++;
                    break;
                }
            }
        }
        return (contador == this.n);
    }

    private boolean[] inicializarUsadas(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = false;
        }
        return arr;
    }

    public static Palabra[] filtrarPalabrasSegunNivel(int nivel, Palabra[] posiblesPalabras) {
        Palabra[] palabras = new Palabra[nivel]; // Array de palabras
        Palabra[] posiblesPalabrasLimpias = limpiarArrayPalabras(posiblesPalabras); // Limpieza de posiblesPalabras sin null
        boolean[] usadas = new boolean[posiblesPalabrasLimpias.length]; //Para comprobar si una palabra la hemos colocado
        int j = nivel; // Variable para saber cuantas palabras coger
        int c = 0; //contador de palabras incorporadas
        Random r = new Random();
        int numeroPalabras = posiblesPalabrasLimpias.length;

        if (nivel > posiblesPalabrasLimpias.length) {
            j = posiblesPalabrasLimpias.length;
        }

        for (int k = 0; k < j; k++) {
            int n = r.nextInt(numeroPalabras); //entre 0 y total - 1
            while (usadas[n] == true) {
                n = r.nextInt(numeroPalabras);
            }
            Palabra p = posiblesPalabrasLimpias[n];
            palabras[c] = p;
            c++;
            usadas[n] = true;
        }

        return palabras;
    }

    private static Palabra[] limpiarArrayPalabras(Palabra[] arrPalabra) { //Devuelve array de palabras sin null
        Palabra[] palabras;
        int cantidadPalabras = palabrasSinNull(arrPalabra);
        palabras = new Palabra[cantidadPalabras];
        int i = 0; //Contador de palabras añadidas
        for (Palabra p : arrPalabra) { //Recorremos todas las palabras
            if (p != null) {
                palabras[i] = p;
                i++;
            }
        }

        return palabras;
    }

    private static int palabrasSinNull(Palabra[] arrPalabra) { //Contador de palabras que no son null
        int c = 0;//Contador
        for (Palabra p : arrPalabra) {
            if (p != null) {
                c++;
            }
        }
        return c;
    }
    
    public static Palabra[] creadorPalabras(Palabra[] arrFichas, int nivel) {
        Palabra[] palabrasCreadas = new Palabra[nivel*5]; 
        boolean[] usadas = new boolean[11];
        int c = 0; //Contador de palabras añadidas
        while (c < nivel*5) {
            Random r1 = new Random(); // Hace referencia a el tamaño de la palabra
            Random r2 = new Random(); // Hace referencia a la letra del arrFichas
            int longitudPalabra = r1.nextInt(12); // tamaño de la palabra 0-11
            while(longitudPalabra == 0){
                longitudPalabra = r1.nextInt(12);
            }
            Palabra p = new Palabra();
            for (int i = 0; i < longitudPalabra; i++) {
                int intLetra = r2.nextInt(11); // letra a seleccionar 0-10 
                while (usadas[intLetra] == true) { // Comprobaremos que no esta usada la letra
                    intLetra = r2.nextInt(11);
                }
                usadas[intLetra] = true;
                p.anadirCaracter(arrFichas[intLetra].getPal()[0]);
            }
            palabrasCreadas[c] = p;
            usadas = new boolean[11];
            c++;
        }
        return palabrasCreadas;
    }
    
    
    public static Palabra[] comprobacionPalabra(Palabra[] arrPalabra, Palabra[] diccionario){ //Comprobamos las palabra dentro del diccionario
        Palabra[] palabras = new Palabra[arrPalabra.length];
        int c = 0;
        for(Palabra p : arrPalabra){
            Palabra encontrada = encontrarPalabra(diccionario,p);
            if(!encontrada.vacia()){
                palabras[c] = p;
                c++;
            }
        }
        
        return palabras;
    }

    @Override
    public String toString() {
        String res = "{'palabra': ";
        for (int i = 0; i < n; i++) {
            res = res + pal[i];
        }
        res += ",'tamaño':" + n + "}";
        return res;
    }
}
