/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import fichero.*;
import palabra.*;
import java.util.*;

public class Game {

    int turnos; // Número de turnos que jugaremos en la partida
    int puntos = 0; // Puntos totales de la partida actual
    String usuario = ""; //Nombre del usuario
    Palabra[] diccionario = null; //Diccionario de palabras
    Rubrica[] rubrica = null; // Rubrica con las letras, sus puntos, y cantidad

    public Game() {

    }

    public void menu() throws Exception { // Menu del juego
        LT lector = new LT();
        this.diccionario = LecturaFicheroBuffer.leerDiccionario("src\\fichero\\esp.dic", 188259); //Leemos el diccionario y lo almacenamos
        char opcion = ' ';
        while (opcion != 's') {  // mientras no salir
            System.out.println("\n\n");
            System.out.println("**************************************");
            System.out.println("*        Menú      *");
            System.out.println("**************************************");
            System.out.println("Escoge una opciÃ³n:");
            System.out.println("   1 Jugar");
            System.out.println("   2 Ver estadísticas");
            System.out.println("   3 Ver rubrica");
            System.out.println("   s Salir");
            System.out.print("Opcion? ");
            opcion = lector.leerCaracter();
            switch (opcion) {
                case '1':
                    Rubrica.generarRúbrica(); // Generamos la rubrica de puntos 
                    rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                    setUsuario();
                    jugar();
                    break;
                case '2':
                    partidas();
                    break;
            }
        }
        System.out.println("**************************************");
        System.out.println("*            Fin opción              *");
        System.out.println("**************************************");
        System.out.println("\n\n");
    }
    public void jugar() throws Exception {

        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0; // Ponemos los puntos a 0
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Palabra[] fichas = generar(); //Generamos las fichas
            cuentaAtras(3); // Cuenta atrás para pensar

            //*************** TO Do: Comprobar si nuestras letras estan dentro de la bolsa de fichas  y que no nos hayamos inventado nada ************************
            Palabra miPalabra = pedirPalabra();
            if (comprobarPalabraEnFichas(miPalabra, fichas)) {
                Palabra e = Palabra.encontrarPalabra(diccionario, miPalabra); //Buscamos la palabra introducida dentro del diccionario

                if (!e.vacia()) { // Si no esta vacía, es decir, si se ha encontrado
                    System.out.println(e);
                    calcularPuntos(e.getPal());
                } else {
                    this.puntos -= 10; //Penalización
                    System.out.println("Palabra no existente, has perdido -10 puntos");
                    System.out.println("Total de puntos: " + this.puntos);
                }
               
            } else {
                System.out.println("Estas haciendo trampas, tienes una penalización de -30");
                this.puntos -= 30;
                System.out.println("Total de puntos: " + this.puntos);
            }
            turno++; // Turno completado
        }
        guardar(); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    public void setTurnos() {
        LT leerNumeroTurno = new LT();
        int t = leerNumeroTurno.leerEntero();
        this.turnos = t;
    }

    public void setUsuario() {
        LT l = new LT();
        System.out.println("¿Cual es su nombre de usuario?");
        String nombre = l.leerLinea();
        this.usuario = nombre;
    }

    public int getTurnos() {
        return this.turnos;
    }

    private Palabra pedirPalabra() {
        LT l = new LT();
        String palabra = l.leerLinea();
        char[] palabraChar = palabra.toCharArray();
        Palabra p = new Palabra();
        for (int j = 0; j < palabraChar.length; j++) {
            p.anadirCaracter(palabraChar[j]);
        }
        return p;
    }

    private void guardar() throws Exception {
        String s = "";
        s += "Nombre: " + this.usuario + ", ";
        s += "Turnos: " + this.turnos + ", ";
        s += "Puntos: " + this.puntos + ", ";
        Date date = Calendar.getInstance().getTime();
        s += "Fecha: " + date;
        EscrituraFichero fw = new EscrituraFichero("src\\fichero\\partidas.txt"); //Fichero que leeremos o crearemos
        fw.escribirString(s); //Escribimos
        fw.cerrar(); //Importante cerrar el buffer
    }

    public void cuentaAtras(int segundos) throws Exception {
        System.out.print("Piense una palabra: ");
        for (int i = segundos; i >= 0; i--) {
            System.out.print(" " + i + " ");
            Thread.sleep(1000); // Simplemente paramos la ejecución durante un segundo
        }
        System.out.println("");
        System.out.println("Escriba una palabra: ");

    }

    public void calcularPuntos(char[] p) throws Exception {
        int total = 0;
        for (int i = 0; i < p.length; i++) {
            for (Rubrica r : this.rubrica) {
                if (r.getLetra() == p[i]) {
                    total += r.getPuntos();
                    this.puntos += r.getPuntos();
                }
            }
        }
        System.out.println("Has ganado " + total + " puntos");
        System.out.println("Total de puntos: " + this.puntos);
    }

    private void partidas() throws Exception {
        System.out.println("");
        System.out.println("****************Registro de partidas****************");
        LecturaFicheroBuffer l = new LecturaFicheroBuffer("src\\fichero\\partidas.txt");
        String linea = l.leerTodoLineaALinea();
        while (linea != "") {
            System.out.println(linea);
            linea = l.leerTodoLineaALinea();
        }
    }

    public Palabra[] generar() throws Exception {
        Palabra[] fichas = new Palabra[11];
        for (int i = 0; i < fichas.length && hayFichas(); i++) {
            fichas[i] = mezclar();
        }

        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i] != null) {
                System.out.print(fichas[i].getPal()[0] + " ");
            }
        }
        return fichas;
    }

    private Palabra mezclar() throws Exception {
        Random r = new Random();
        int numeroLetras = this.rubrica.length;
        int n = r.nextInt(numeroLetras); //entre 0 y total
        while (this.rubrica[n].getCantidad() == 0) {
            n = r.nextInt(numeroLetras);
        }
        this.rubrica[n].setCantidad(-1);
        Palabra l = new Palabra();
        l.anadirCaracter(this.rubrica[n].getLetra());
        Rubrica.numeroLetras -= 1;
        System.out.println(Rubrica.numeroLetras);
        return l;
    }

    private boolean hayFichas() {
        int i = 0;
        for (Rubrica r : this.rubrica) {
            if (r.getCantidad() != 0) {
                i++;
            }
        }
        return (i >= 1);
    }

    private boolean comprobarPalabraEnFichas(Palabra p, Palabra[] fichas) {
        int contador = 0;
        Palabra[] arrFichas = fichas;
        boolean[] usadas = new boolean[fichas.length];
        usadas = inicializarUsadas(usadas);
        for (int j = 0; j < p.getN(); j++) {
            for (int i = 0; i < arrFichas.length; i++) {
                if (p.getPal()[j] == arrFichas[i].getPal()[0] && usadas[i] == false) {
                    usadas[i] = true;
                    contador++;
                    break;
                }
            }
        }
        return (contador == p.getN());
    }

    private boolean[] inicializarUsadas(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = false;
        }
        return arr;
    }

}
