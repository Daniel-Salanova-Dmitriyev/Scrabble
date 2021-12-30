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
    int puntosBot = 0; //Puntos del bot
    int segundos = 8; // Segundos para pensar
    String usuario = ""; //Nombre del usuario
    String idioma;
    Palabra[] diccionario = null; //Diccionario de palabras
    Rubrica[] rubrica = null; // Rubrica con las letras, sus puntos, y cantidad

    public Game() {

    }

    public void menu() throws Exception { // Menu del juego
        LT lector = new LT();
        char opcion = ' ';
        while (opcion != 's') {  // mientras no salir
            System.out.println("\n\n");
            System.out.println("**************************************");
            System.out.println("*        Menú      *");
            System.out.println("**************************************");
            System.out.println("Escoge una opciÃ³n:");
            System.out.println("   1 Jugar");
            System.out.println("   2 Ver estadísticas");
            System.out.println("   s Salir");
            System.out.print("Opcion? ");
            opcion = lector.leerCaracter();
            switch (opcion) {
                case '1':
                    char opcionJugar = ' ';
                    while (opcionJugar != 's') {
                        System.out.println("**************************************");
                        System.out.println("*        Menú      *");
                        System.out.println("**************************************");
                        System.out.println("Escoge una opciÃ³n:");
                        System.out.println("   1 Un solo jugador");
                        System.out.println("   2 Bot extremo");
                        System.out.println("   3 Bot nivelado");
                        System.out.println("   s Salir");
                        System.out.print("Opcion? ");
                        opcionJugar = lector.leerCaracter();
                        switch (opcionJugar) {
                            case '1':
                                Rubrica.generarRúbrica(); // Generamos la rubrica de puntos 
                                rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                jugar();
                                break;
                            case '2':
                                Rubrica.generarRúbrica(); // Generamos la rubrica de puntos 
                                rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                jugarBotExtremo();
                                break;
                            case '3':
                                Rubrica.generarRúbrica(); // Generamos la rubrica de puntos 
                                rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                int nivel = 0;

                                while (nivel <= 0 || nivel > 10) {
                                    System.out.println("Elija un nivel del 1-10");
                                    nivel = lector.leerEntero();
                                }

                                jugarBotNivelado(nivel);
                                break;
                        }
                    }

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

    private void seleccionarIdioma() throws Exception {
        int eleccion = 0;
        int cantidadPalabras = 0;
        String ruta = " ";
        LT l = new LT();
        System.out.println(" ");
        System.out.println("Seleccione un idioma: ");
        System.out.println("   1 Castellano");
        System.out.println("   2 Catalan");
        System.out.println("¿Cual elige?");
        while (eleccion < 1 || eleccion > 2) {
            eleccion = l.leerEntero();
        }
        if (eleccion == 1) {
            ruta = "src\\fichero\\esp.dic";
            cantidadPalabras = Palabra.cantidadPalabrasEnDiccionario(ruta); //188259
            this.idioma = "esp";
        } else {
            ruta = "src\\fichero\\cat.dic";
            cantidadPalabras = Palabra.cantidadPalabrasEnDiccionario(ruta); //87719
            this.idioma = "cat";
        }
        this.diccionario = LecturaFicheroBuffer.leerDiccionario(ruta, cantidadPalabras); //Leemos el diccionario y lo almacenamos
    }

    private void jugar() throws Exception {
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0; // Ponemos los puntos a 0
        if (this.turnos > 9) {
            this.turnos = 9;
        }
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Palabra[] fichas = generar(true); //Generamos las fichas
            cuentaAtras(this.segundos); // Cuenta atrás para pensar

            Palabra miPalabra = Palabra.pedirPalabra();
            if (miPalabra.comprobarPalabraEnFichas(fichas)) { //Primera comprobación, si te has inventado letras no existentes dentro de la bolsa de fichas
                Palabra e = Palabra.encontrarPalabra(diccionario, miPalabra); //Buscamos la palabra introducida dentro del diccionario

                if (!e.vacia()) { // Si no esta vacía, es decir, si se ha encontrado
                    System.out.println(e);
                    calcularPuntos(e.getPal());
                } else { // Palabra no existente
                    this.puntos -= 10; //Penalización
                    System.out.println("Palabra no existente, has perdido -10 puntos");
                    System.out.println("Total de puntos: " + this.puntos);
                }

            } else { //Palabra con letras que no tenemos
                System.out.println("Estas haciendo trampas, tienes una penalización de -30");
                this.puntos -= 30;
                System.out.println("Total de puntos: " + this.puntos);
            }
            turno++; // Turno completado
        }

        guardar(false, 4); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    private void jugarBotExtremo() throws Exception {
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        if (this.turnos > 5) {
            this.turnos = 5;
        }
        this.puntos = 0; // Ponemos los puntos a 0
        this.puntosBot = 0; // Ponemos los puntos del bot a 0
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Palabra[] fichasJugador = generar(true); //Generamos las fichas
            Palabra[] fichasContrincante = generar(false); //Generamos las fichas del contrincante
            Palabra[] posiblesPalabras = Palabra.comprobarEnFichasPalabras(fichasContrincante, this.diccionario); //Todas las palabras que se pueden crear con las fichas que tiene
            Palabra elegidaBot = mejorPalabra(posiblesPalabras); //Palabra con mayor puntuación
            cuentaAtras(this.segundos); // Cuenta atrás para pensar

            Palabra miPalabra = Palabra.pedirPalabra();
            if (miPalabra.comprobarPalabraEnFichas(fichasJugador)) {
                Palabra e = Palabra.encontrarPalabra(diccionario, miPalabra); //Buscamos la palabra introducida dentro del diccionario

                if (!e.vacia()) { // Si no esta vacía, es decir, si se ha encontrado
                    System.out.println(e);
                    calcularPuntos(e.getPal());
                } else {
                    this.puntos -= 10; //Penalización
                    System.out.println("Palabra no existente, has perdido -10 puntos");
                }

            } else {
                System.out.println("Estas haciendo trampas, tienes una penalización de -30");
                this.puntos -= 30;
            }

            System.out.println("Total de puntos: " + this.puntos);
            System.out.println("Total de puntos del bot: " + this.puntosBot);
            turno++; // Turno completado
        }

        guardar(true, resultadoPartida()); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    private int resultadoPartida() {
        int i = 0;
        if (this.puntosBot == this.puntos) {
            System.out.println("Partida empatada");
            i = 0;
        }
        if (this.puntosBot > this.puntos) {
            System.out.println("Partida perdida");
            i = 2;
        } else {
            i = 1;
            System.out.println("Partida ganada");
        }
        return i;
    }

    private void jugarBotNivelado(int nivel) throws Exception {
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        if (this.turnos > 5) {
            this.turnos = 5;
        }
        this.puntos = 0; // Ponemos los puntos a 0
        this.puntosBot = 0; // Ponemos los puntos del bot a 0
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Palabra[] fichasJugador = generar(true); //Generamos las fichas
            Palabra[] fichasContrincante = generar(false); //Generamos las fichas del contrincante
            Palabra[] posiblesPalabras = Palabra.comprobarEnFichasPalabras(fichasContrincante, this.diccionario);
            Palabra[] filtradasPorNivel = Palabra.filtrarPalabrasSegunNivel(nivel, posiblesPalabras); // Cogerá de todas las palabras que puede crear con sus fichas la misma cantidad que el nivel que tiene de manera aleatoria
            Palabra elegidaBot = mejorPalabra(filtradasPorNivel); // Se quedará la mayor puntuación tiene
            cuentaAtras(this.segundos); // Cuenta atrás para pensar

            Palabra miPalabra = Palabra.pedirPalabra();
            if (miPalabra.comprobarPalabraEnFichas(fichasJugador)) {
                Palabra e = Palabra.encontrarPalabra(diccionario, miPalabra); //Buscamos la palabra introducida dentro del diccionario

                if (!e.vacia()) { // Si no esta vacía, es decir, si se ha encontrado
                    System.out.println(e);
                    calcularPuntos(e.getPal());
                } else {
                    this.puntos -= 10; //Penalización
                    System.out.println("Palabra no existente, has perdido -10 puntos");

                }

            } else {
                System.out.println("Estas haciendo trampas, tienes una penalización de -30");
                this.puntos -= 30;

            }
            System.out.println("Total de puntos: " + this.puntos);
            System.out.println("Total de puntos del bot: " + this.puntosBot);
            turno++; // Turno completado

        }
        guardar(true, resultadoPartida()); //Guardamos datos de la partida dentro del fichero partidas.txt
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

    private void guardar(boolean bot, int resultado) throws Exception { //Guardar partida dentro del fichero de partidas
        String s = "";
        s += "Nombre: " + this.usuario;
        s += ", Turnos: " + this.turnos;
        s += ", Puntos: " + this.puntos;
        Date date = Calendar.getInstance().getTime();



        EscrituraFichero fw = new EscrituraFichero("src\\fichero\\partidas.txt"); //Fichero que leeremos o crearemos
        if (bot) {
            s += ", Puntos bot: " + this.puntosBot;
            if (resultado == 0) {
                s += ", Resultado: Empate";
            }
            if (resultado == 1) {
                s += ", Resultado: Ganada";
            } else {
                s += ", Resultado: Derrota";
            }

        }
       
        String codigoIdioma = "";
        for (char c : this.idioma.toCharArray()) {
            codigoIdioma += c;
        }
        s += ", Idioma: " + codigoIdioma;
        s += ", Fecha: " + date;
        fw.escribirString(s); //Escribimos
        fw.cerrar(); //Importante cerrar el buffer
    }

    private void cuentaAtras(int segundos) throws Exception {
        System.out.println("");
        System.out.print("Piense una palabra: ");
        for (int i = segundos; i >= 0; i--) {
            System.out.print(" " + i + " ");
            Thread.sleep(1000); // Simplemente paramos la ejecución durante un segundo
        }
        System.out.println("");
        System.out.println("Escriba una palabra: ");

    }

    private void calcularPuntos(char[] p) throws Exception {
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

    private Palabra[] generar(boolean player) throws Exception {
        Palabra[] fichas = new Palabra[11];
        for (int i = 0; i < fichas.length && hayFichas(); i++) {
            fichas[i] = mezclar();
        }

        if (player) {
            for (int i = 0; i < fichas.length; i++) {
                if (fichas[i] != null) {
                    System.out.print(fichas[i].getPal()[0] + " ");
                }
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

    private Palabra mejorPalabra(Palabra[] posiblesPalabras) { //Busca la palabra con mayor puntuacion
        Palabra mejor = new Palabra();
        int puntuacionPrincipal = 0;
        int puntuacionSecundaria = 0;
        for (Palabra p : posiblesPalabras) {
            if (p != null) {
                for (int c = 0; c < p.getN(); c++) {
                    for (Rubrica r : this.rubrica) {
                        if (r.getLetra() == p.getPal()[c]) {
                            puntuacionSecundaria += r.getPuntos();

                        }
                    }
                }
                if (puntuacionSecundaria >= puntuacionPrincipal) {
                    puntuacionPrincipal = puntuacionSecundaria;
                    mejor = p;
                }
                puntuacionSecundaria = 0;
            }
        }
        System.out.println(" ");
        System.out.print(mejor);
        System.out.print(" Mejor Palabra bot es , de puntos: " + puntuacionPrincipal);
        this.puntosBot += puntuacionPrincipal;
        return mejor;
    }

}
