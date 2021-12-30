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
        for (Palabra p : diccionario) {
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

    public static int cantidadPalabrasEnDiccionario(String nombre) throws Exception {
        int c = 0;
        LecturaFicheroBuffer f = new LecturaFicheroBuffer(nombre);
        Palabra p = f.leerPalabra();
        while (!p.vacia()) {
            c++;
            p = f.leerPalabra();
        }
        f.cerrar();
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
        Palabra[] palabrasEncontradas = new Palabra[400]; //Test
        boolean[] usadas = new boolean[fichas.length];
        int contadorPalabra = 0;
        int similitud = 0;
        for (Palabra p : diccionario) { // Recorremos el diccionario entero
            for (int i = 0; i < p.getN(); i++) { // Recorremos cada palabra letra de la palabra
                for (int j = 0; j < fichas.length && fichas[j] != null; j++) { //Buscamos letra dentro de nuestras fichas
                    if (fichas[j].getPal()[0] == p.getPal()[i] && usadas[j] == false) {
                        usadas[j] = true;
                        similitud++;
                        break;
                    }
                }

            }

            if (similitud == p.getN() && contadorPalabra + 1 < 400) { //Comprobamos que este entera la palabra en nuestras fichas
                palabrasEncontradas[contadorPalabra] = p;
                contadorPalabra++;
            }

            similitud = 0;
            usadas = new boolean[fichas.length];
        }
        return palabrasEncontradas;
    }

    public boolean comprobarPalabraEnFichas(Palabra[] fichas) { //Metodo para usuario, comprobará que no consume fichas de más y que no se inventa letras
        int contador = 0;
        Palabra[] arrFichas = fichas;
        boolean[] usadas = new boolean[fichas.length];
        usadas = inicializarUsadas(usadas);
        for (int j = 0; j < this.n; j++) {
            for (int i = 0; i < arrFichas.length && arrFichas[i] != null; i++) {
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
        int j = nivel;
        int c = 0;
        Random r = new Random();
        int numeroPalabras = posiblesPalabrasLimpias.length;

        if (nivel > posiblesPalabrasLimpias.length) {
            j = posiblesPalabrasLimpias.length;
        }

        for (int k = 0; k < j; k++) {
            int n = r.nextInt(numeroPalabras); //entre 0 y total
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

    private static Palabra[] limpiarArrayPalabras(Palabra[] arrPalabra) {
        Palabra[] palabras;
        int cantidadPalabras = palabrasSinNull(arrPalabra);
        palabras = new Palabra[cantidadPalabras];
        int i = 0;
        for (Palabra p : arrPalabra) {
            if (p != null) {
                palabras[i] = p;
                i++;
            }
        }

        return palabras;
    }

    private static int palabrasSinNull(Palabra[] arrPalabra) {
        int c = 0;
        for (Palabra p : arrPalabra) {
            if (p != null) {
                c++;
            }
        }
        return c;
    }

    /*
    
    public static boolean comprobarPalabraEnFichas(Palabra p, Palabra[] fichas) { //Metodo para usuario, comprobará que no consume fichas de más y que no se inventa letras
        int contador = 0;
        Palabra[] arrFichas = fichas;
        boolean[] usadas = new boolean[fichas.length];
        usadas = inicializarUsadas(usadas);
        for (int j = 0; j < p.getN(); j++) {
            for (int i = 0; i < arrFichas.length && arrFichas[i] != null; i++) {
                if (p.getPal()[j] == arrFichas[i].getPal()[0] && usadas[i] == false) {
                    usadas[i] = true;
                    contador++;
                    break;
                }
            }
        }
        return (contador == p.getN());
    }
    
    
     */
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
